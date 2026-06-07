package com.Stumate.project.domain.userSubject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class UserSubjectResDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Info {
        private Long userSubjectId;
        private String subjectName;
    }
}