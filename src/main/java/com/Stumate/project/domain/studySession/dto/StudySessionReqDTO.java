package com.Stumate.project.domain.studySession.dto;

import com.Stumate.project.domain.studySession.enums.LocationType;
import lombok.Getter;

public class StudySessionReqDTO {

    @Getter
    public static class Start {
        private String subject;
        private LocationType location;
    }

    @Getter
    public static class Finish {
        private Integer focusScore;
        private Integer pauseCount;
    }
}