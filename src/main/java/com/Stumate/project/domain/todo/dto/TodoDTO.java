package com.Stumate.project.domain.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TodoDTO {

    @Getter
    public static class Create {
        private String content;
        private LocalDate todoDate;
    }

    @Getter
    public static class Update {
        private String content;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Info {
        private Long todoId;
        private String content;
        private LocalDate todoDate;
        private Boolean isCompleted;
        private LocalDateTime completedAt;
        private LocalDateTime createdAt;
    }
}
