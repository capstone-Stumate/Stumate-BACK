package com.Stumate.project.domain.studySession.dto;

import com.Stumate.project.domain.studySession.enums.LocationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class StudySessionDTO {

    @Getter
    public static class Start {
        private String subject;
        private LocationType location;
    }

    @Getter
    public static class Finish {
        private Integer durationSec;
        private Integer focusScore;
        private Integer pauseCount;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Info {
        private Long sessionId;
        private String subject;
        private LocationType location;
        private LocalDateTime startedAt;
        private LocalDateTime endedAt;
        private Integer durationSec;
        private Integer focusScore;
        private Integer pauseCount;
    }
}
