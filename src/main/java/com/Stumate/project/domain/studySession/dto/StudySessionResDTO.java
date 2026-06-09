package com.Stumate.project.domain.studySession.dto;

import com.Stumate.project.domain.studySession.enums.LocationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class StudySessionResDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class SessionInfo {
        private Long sessionId;
        private Long userSubjectId;
        private LocationType location;
        private LocalDateTime startedAt;
        private LocalDateTime endedAt;
        private Integer durationSec;
        private Integer focusScore;
        private Integer pauseCount;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class WeeklyStats {
        private Long totalDurationSec;
    }
}