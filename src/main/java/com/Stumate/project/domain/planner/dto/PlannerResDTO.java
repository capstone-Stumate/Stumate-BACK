package com.Stumate.project.domain.planner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class PlannerResDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PlannerInfo {
        private String learningTypeId;
        private String studyTypeDescription;
        private Integer focusPeakStart;
        private Integer focusPeakEnd;
        private List<HourlyFocus> focusTimeData;
        private PomodoroData pomodoroData;
        private StudyStats studyStats;
        private List<LocationEfficiency> locationEfficiencies;
        private List<SubjectFocus> subjectFocuses;
        private String aiPlanner;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class HourlyFocus {
        private int hour;
        private double focusScore;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PomodoroData {
        private double avgFocusMinutes;
        private double avgPauseCount;
        private int recommendFocusMinutes;
        private int recommendBreakMinutes;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class StudyStats {
        private long todayStudyTime;
        private long totalStudyTime;
        private double completionRate;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class LocationEfficiency {
        private String locationId;
        private double score;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class SubjectFocus {
        private Long subjectId;
        private String subjectName;
        private double score;
    }
}