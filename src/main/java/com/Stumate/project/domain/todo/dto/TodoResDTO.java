package com.Stumate.project.domain.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TodoResDTO {

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

    @Getter
    @Builder
    @AllArgsConstructor
    public static class DailyGroup {
        private LocalDate todoDate;
        private List<Info> todos;
    }
}