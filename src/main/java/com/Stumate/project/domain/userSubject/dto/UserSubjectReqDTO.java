package com.Stumate.project.domain.userSubject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class UserSubjectReqDTO {

    @Getter
    public static class Create {

        @NotBlank(message = "과목명을 입력해주세요.")
        private String subjectName;
    }
}