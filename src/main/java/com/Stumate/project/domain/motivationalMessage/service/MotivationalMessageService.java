package com.Stumate.project.domain.motivationalMessage.service;

import com.Stumate.project.global.config.AzureOpenAIConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MotivationalMessageService {

    private final AzureOpenAIConfig azureOpenAIConfig;
    private final RestTemplate restTemplate;

    public String generateMessage(String userName, double weeklyCompletionRate) {
        String timeSlot = getTimeSlot();
        String prompt = buildPrompt(userName, timeSlot, weeklyCompletionRate);

        String url = azureOpenAIConfig.getEndpoint()
                + "/openai/deployments/" + azureOpenAIConfig.getDeploymentName()
                + "/chat/completions?api-version=2024-02-01";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", azureOpenAIConfig.getApiKey());

        Map<String, Object> body = Map.of(
                "messages", List.of(
                        Map.of("role", "system", "content", "당신은 공부하는 학생을 응원하는 따뜻한 AI 친구입니다. 2문장 이내로 짧고 따뜻하게 응원해주세요."),
                        Map.of("role", "user", "content", prompt)
                ),
                "max_tokens", 150,
                "temperature", 0.8
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            Map<String, Object> responseBody = response.getBody();
            List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            return (String) message.get("content");
        } catch (Exception e) {
            return userName + " 님, 오늘도 화이팅! 🔥";
        }
    }

    // 현재 시간대 판단
    private String getTimeSlot() {
        int hour = LocalTime.now().getHour();
        if (hour >= 5 && hour < 12) return "아침";
        else if (hour >= 12 && hour < 18) return "낮";
        else if (hour >= 18 && hour < 22) return "저녁";
        else return "밤";
    }

    // 프롬프트 생성
    private String buildPrompt(String userName, String timeSlot, double weeklyCompletionRate) {
        return String.format(
                "%s 님이 %s에 공부하러 앱을 열었어요. " +
                        "이번주 할 일 수행률은 %.0f%%예요. " +
                        "이 정보를 바탕으로 따뜻하고 동기부여가 되는 응원 메시지를 2문장 이내로 작성해주세요.",
                userName, timeSlot, weeklyCompletionRate * 100
        );
    }
}