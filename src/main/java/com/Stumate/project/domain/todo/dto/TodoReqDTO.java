package com.Stumate.project.domain.todo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

public class TodoReqDTO {

    @Getter
    public static class Create {

        @NotBlank(message = "할 일 내용을 입력해주세요.")
        private String content;

        @NotNull(message = "날짜를 입력해주세요.")
        private LocalDate todoDate;
    }
}