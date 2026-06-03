package com.Stumate.project.domain.todo.dto;

import lombok.Getter;
import java.time.LocalDate;

public class TodoReqDTO {

    @Getter
    public static class Create {
        private String content;
        private LocalDate todoDate;
    }
}