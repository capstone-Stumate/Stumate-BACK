package com.Stumate.project.domain.planner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class PlannerResDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Info {
        // AI 분석 결과
        private String studyType;           // 예: 야행성 학습형
        private String studyTypeDescription; // 예: 밤 10시 이후에 집중도가 급격히 올라가요.

        // 시간대별 평균 집중도
        private List<HourlyFocus> hourlyFocusList;

        // 포모도로 분석
        private double avgFocusMinutes;     // 평균 집중 유지 (분)
        private double avgPauseCount;       // 평균 일시정지 횟수
        private int recommendedCycleMinutes; // 추천 사이클 (분)

        // 오늘/이번주 통계
        private long todayStudySec;         // 오늘 공부 시간 (초)
        private long totalStudySec;         // 총 공부 시간 (초)
        private double completionRate;      // 수행률 (0.0 ~ 1.0)

        // 장소별 효율
        private List<LocationStat> locationStats;

        // 과목별 집중도
        private List<SubjectStat> subjectStats;

        // AI 플래너 추천
        private String aiPlanner;           // AI가 생성한 플래너 텍스트
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class HourlyFocus {
        private int hour;           // 시간 (0~23)
        private double avgScore;    // 평균 집중도
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class LocationStat {
        private String location;    // 장소명
        private double avgScore;    // 평균 집중도
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class SubjectStat {
        private String subjectName; // 과목명
        private double avgScore;    // 평균 집중도
    }
}