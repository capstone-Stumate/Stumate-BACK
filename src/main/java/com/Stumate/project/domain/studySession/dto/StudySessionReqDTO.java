package com.Stumate.project.domain.studySession.dto;

import com.Stumate.project.domain.studySession.enums.LocationType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class StudySessionReqDTO {

    @Getter
    public static class Start {

        @NotNull(message = "과목을 선택해주세요.")
        private Long userSubjectId; // subject VARCHAR → userSubjectId Long으로 수정!

        @NotNull(message = "장소를 선택해주세요.")
        private LocationType location;
    }

    @Getter
    public static class Finish {
        private Integer focusScore;
        private Integer pauseCount;
    }
}