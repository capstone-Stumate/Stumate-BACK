package com.Stumate.project.domain.planner.service;

import com.Stumate.project.domain.planner.dto.PlannerResDTO;
import com.Stumate.project.domain.studySession.entity.StudySession;
import com.Stumate.project.domain.studySession.repository.StudySessionRepository;
import com.Stumate.project.domain.todo.entity.Todo;
import com.Stumate.project.domain.todo.repository.TodoRepository;
import com.Stumate.project.domain.userSubject.entity.UserSubject;
import com.Stumate.project.domain.userSubject.repository.UserSubjectRepository;
import com.Stumate.project.global.config.AzureOpenAIConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlannerService {

    private final StudySessionRepository studySessionRepository;
    private final TodoRepository todoRepository;
    private final UserSubjectRepository userSubjectRepository;
    private final AzureOpenAIConfig azureOpenAIConfig;
    private final RestTemplate restTemplate;

    public PlannerResDTO.PlannerInfo getPlanner(Long userId) {
        List<StudySession> allSessions = studySessionRepository.findByUserId(userId);
        List<StudySession> completedSessions = allSessions.stream()
                .filter(s -> s.getEndedAt() != null)
                .collect(Collectors.toList());

        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        long todayStudyTime = completedSessions.stream()
                .filter(s -> s.getStartedAt().isAfter(todayStart))
                .mapToLong(s -> s.getDurationSec() != null ? s.getDurationSec() : 0)
                .sum();

        long totalStudyTime = completedSessions.stream()
                .mapToLong(s -> s.getDurationSec() != null ? s.getDurationSec() : 0)
                .sum();

        LocalDate weekStart = LocalDate.now().with(java.time.DayOfWeek.MONDAY);
        LocalDate weekEnd = LocalDate.now().with(java.time.DayOfWeek.SUNDAY);
        List<Todo> weeklyTodos = todoRepository.findAllByUserIdAndTodoDateBetweenOrderByTodoDateAsc(userId, weekStart, weekEnd);
        double completionRate = weeklyTodos.isEmpty() ? 0.0 :
                (double) weeklyTodos.stream().filter(Todo::getIsCompleted).count() / weeklyTodos.size();

        Map<Integer, List<Integer>> hourlyScores = new HashMap<>();
        for (StudySession s : completedSessions) {
            if (s.getFocusScore() != null) {
                int hour = s.getStartedAt().getHour();
                hourlyScores.computeIfAbsent(hour, k -> new ArrayList<>()).add(s.getFocusScore());
            }
        }
        List<PlannerResDTO.HourlyFocus> focusTimeData = hourlyScores.entrySet().stream()
                .map(e -> PlannerResDTO.HourlyFocus.builder()
                        .hour(e.getKey())
                        .focusScore(e.getValue().stream().mapToInt(i -> i).average().orElse(0))
                        .build())
                .sorted(Comparator.comparingInt(PlannerResDTO.HourlyFocus::getHour))
                .collect(Collectors.toList());

        int peakHour = focusTimeData.stream()
                .max(Comparator.comparingDouble(PlannerResDTO.HourlyFocus::getFocusScore))
                .map(PlannerResDTO.HourlyFocus::getHour)
                .orElse(12);

        double avgFocusMinutes = completedSessions.stream()
                .filter(s -> s.getDurationSec() != null && s.getPauseCount() != null && s.getPauseCount() > 0)
                .mapToDouble(s -> (s.getDurationSec() / 60.0) / (s.getPauseCount() + 1))
                .average().orElse(25.0);

        double avgPauseCount = completedSessions.stream()
                .filter(s -> s.getPauseCount() != null)
                .mapToInt(StudySession::getPauseCount)
                .average().orElse(0.0);

        Map<String, List<Integer>> locationScores = new HashMap<>();
        for (StudySession s : completedSessions) {
            if (s.getFocusScore() != null && s.getLocation() != null) {
                locationScores.computeIfAbsent(s.getLocation().name(), k -> new ArrayList<>()).add(s.getFocusScore());
            }
        }
        List<PlannerResDTO.LocationEfficiency> locationEfficiencies = locationScores.entrySet().stream()
                .map(e -> PlannerResDTO.LocationEfficiency.builder()
                        .locationId(e.getKey())
                        .score(e.getValue().stream().mapToInt(i -> i).average().orElse(0))
                        .build())
                .collect(Collectors.toList());

        Map<Long, List<Integer>> subjectScores = new HashMap<>();
        for (StudySession s : completedSessions) {
            if (s.getFocusScore() != null && s.getUserSubjectId() != null) {
                subjectScores.computeIfAbsent(s.getUserSubjectId(), k -> new ArrayList<>()).add(s.getFocusScore());
            }
        }
        List<UserSubject> userSubjects = userSubjectRepository.findByUserIdAndDeletedAtIsNull(userId);
        Map<Long, String> subjectNameMap = userSubjects.stream()
                .collect(Collectors.toMap(UserSubject::getUserSubjectId, UserSubject::getSubjectName));

        List<PlannerResDTO.SubjectFocus> subjectFocuses = subjectScores.entrySet().stream()
                .map(e -> PlannerResDTO.SubjectFocus.builder()
                        .subjectId(e.getKey())
                        .subjectName(subjectNameMap.getOrDefault(e.getKey(), "알 수 없음"))
                        .score(e.getValue().stream().mapToInt(i -> i).average().orElse(0))
                        .build())
                .collect(Collectors.toList());

        String aiPlanner = generateAiPlanner(completedSessions, weeklyTodos,
                focusTimeData, avgFocusMinutes, completionRate, subjectFocuses);

        String learningTypeId = determineStudyType(focusTimeData);
        String studyTypeDescription = getStudyTypeDescription(focusTimeData);

        return PlannerResDTO.PlannerInfo.builder()
                .learningTypeId(learningTypeId)
                .studyTypeDescription(studyTypeDescription)
                .focusPeakStart(peakHour)
                .focusPeakEnd(peakHour + 2)
                .focusTimeData(focusTimeData)
                .pomodoroData(PlannerResDTO.PomodoroData.builder()
                        .avgFocusMinutes(Math.round(avgFocusMinutes))
                        .avgPauseCount(Math.round(avgPauseCount * 10.0) / 10.0)
                        .recommendFocusMinutes((int) Math.round(avgFocusMinutes))
                        .recommendBreakMinutes(5)
                        .build())
                .studyStats(PlannerResDTO.StudyStats.builder()
                        .todayStudyTime(todayStudyTime)
                        .totalStudyTime(totalStudyTime)
                        .completionRate(completionRate)
                        .build())
                .locationEfficiencies(locationEfficiencies)
                .subjectFocuses(subjectFocuses)
                .aiPlanner(aiPlanner)
                .build();
    }

    private String generateAiPlanner(List<StudySession> sessions, List<Todo> todos,
                                     List<PlannerResDTO.HourlyFocus> hourlyFocus,
                                     double avgFocusMinutes, double completionRate,
                                     List<PlannerResDTO.SubjectFocus> subjectFocuses) {
        String todoList = todos.stream()
                .filter(t -> !t.getIsCompleted())
                .map(t -> "- " + t.getContent())
                .collect(Collectors.joining("\n"));
        if (todoList.isEmpty()) todoList = "없음";

        String morningPeak = hourlyFocus.stream()
                .filter(h -> h.getHour() >= 6 && h.getHour() < 12)
                .max(Comparator.comparingDouble(PlannerResDTO.HourlyFocus::getFocusScore))
                .map(h -> h.getHour() + "시 (집중도 " + String.format("%.1f", h.getFocusScore()) + "점)")
                .orElse("데이터 없음");

        String afternoonPeak = hourlyFocus.stream()
                .filter(h -> h.getHour() >= 12 && h.getHour() < 18)
                .max(Comparator.comparingDouble(PlannerResDTO.HourlyFocus::getFocusScore))
                .map(h -> h.getHour() + "시 (집중도 " + String.format("%.1f", h.getFocusScore()) + "점)")
                .orElse("데이터 없음");

        String eveningPeak = hourlyFocus.stream()
                .filter(h -> h.getHour() >= 18)
                .max(Comparator.comparingDouble(PlannerResDTO.HourlyFocus::getFocusScore))
                .map(h -> h.getHour() + "시 (집중도 " + String.format("%.1f", h.getFocusScore()) + "점)")
                .orElse("데이터 없음");

        String subjectInfo = subjectFocuses.stream()
                .sorted(Comparator.comparingDouble(PlannerResDTO.SubjectFocus::getScore).reversed())
                .map(s -> s.getSubjectName() + " (집중도 " + String.format("%.1f", s.getScore()) + "점)")
                .collect(Collectors.joining(", "));
        if (subjectInfo.isEmpty()) subjectInfo = "데이터 없음";

        StringBuilder patternBuilder = new StringBuilder();
        if (!morningPeak.equals("데이터 없음"))
            patternBuilder.append("- 오전 최고 집중 시간대: ").append(morningPeak).append("\n");
        if (!afternoonPeak.equals("데이터 없음"))
            patternBuilder.append("- 오후 최고 집중 시간대: ").append(afternoonPeak).append("\n");
        if (!eveningPeak.equals("데이터 없음"))
            patternBuilder.append("- 저녁 최고 집중 시간대: ").append(eveningPeak).append("\n");
        if (avgFocusMinutes > 0)
            patternBuilder.append(String.format("- 평균 집중 유지 시간: %.0f분\n", avgFocusMinutes));
        patternBuilder.append(String.format("- 이번주 수행률: %.0f%%\n", completionRate * 100));
        if (!subjectInfo.equals("데이터 없음"))
            patternBuilder.append("- 과목별 집중도 (높은 순): ").append(subjectInfo).append("\n");

        String patternInfo = patternBuilder.length() > 0 ? patternBuilder.toString() : "아직 공부 데이터가 없습니다.\n";

        String prompt = String.format(
                "학생의 공부 패턴 데이터를 분석해서 오늘의 학습 순서를 추천해주세요.\n\n" +
                        "[공부 패턴]\n%s\n" +
                        "[미완료 할 일]\n%s\n\n" +
                        "위 실제 데이터만 참고해서 아래 형식으로 추천해주세요.\n" +
                        "데이터가 없는 시간대는 절대 언급하지 마세요.\n\n" +
                        "📚 낮 시간 추천\n" +
                        "(집중도가 높은 과목을 먼저, 어떤 순서로 공부하면 좋은지)\n\n" +
                        "🌙 저녁 시간 추천\n" +
                        "(집중도가 높은 과목을 먼저, 어떤 순서로 공부하면 좋은지)\n\n" +
                        "시간은 적지 말고, 과목과 순서 위주로 간결하게 작성해주세요.",
                patternInfo, todoList
        );

        String url = azureOpenAIConfig.getEndpoint()
                + "/openai/deployments/" + azureOpenAIConfig.getDeploymentName()
                + "/chat/completions?api-version=2024-02-01";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", azureOpenAIConfig.getApiKey());

        Map<String, Object> body = Map.of(
                "messages", List.of(
                        Map.of("role", "system", "content",
                                "당신은 학습 플래너 전문가입니다. 시간은 적지 말고 과목과 순서 위주로 간결하게 답변해주세요."),
                        Map.of("role", "user", "content", prompt)
                ),
                "max_tokens", 400,
                "temperature", 0.7
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            Map<String, Object> responseBody = response.getBody();
            List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            return (String) message.get("content");
        } catch (Exception e) {
            log.error("AI 플래너 생성 실패: {}", e.getMessage());
            return "📚 낮 시간 추천\n할 일 목록을 집중도 높은 과목부터 순서대로 공부해보세요.\n\n🌙 저녁 시간 추천\n복습 위주로 가볍게 정리해보세요.";
        }
    }

    private String determineStudyType(List<PlannerResDTO.HourlyFocus> hourlyFocusList) {
        if (hourlyFocusList.isEmpty()) return "분석 중";
        int peakHour = hourlyFocusList.stream()
                .max(Comparator.comparingDouble(PlannerResDTO.HourlyFocus::getFocusScore))
                .map(PlannerResDTO.HourlyFocus::getHour)
                .orElse(12);
        if (peakHour >= 22 || peakHour < 4) return "야행성 학습형";
        else if (peakHour >= 4 && peakHour < 10) return "아침형 학습형";
        else if (peakHour >= 10 && peakHour < 14) return "오전형 학습형";
        else if (peakHour >= 14 && peakHour < 18) return "오후형 학습형";
        else return "저녁형 학습형";
    }

    private String getStudyTypeDescription(List<PlannerResDTO.HourlyFocus> hourlyFocusList) {
        if (hourlyFocusList.isEmpty()) return "공부 세션을 쌓으면 학습 유형을 분석해드릴게요!";
        int peakHour = hourlyFocusList.stream()
                .max(Comparator.comparingDouble(PlannerResDTO.HourlyFocus::getFocusScore))
                .map(PlannerResDTO.HourlyFocus::getHour)
                .orElse(12);
        double peakScore = hourlyFocusList.stream()
                .max(Comparator.comparingDouble(PlannerResDTO.HourlyFocus::getFocusScore))
                .map(PlannerResDTO.HourlyFocus::getFocusScore)
                .orElse(0.0);
        return String.format("%d시 구간 평균 %.1f점으로 최고 성과를 냅니다.", peakHour, peakScore);
    }
}