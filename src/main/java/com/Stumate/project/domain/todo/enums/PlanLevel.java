package com.Stumate.project.domain.todo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlanLevel {
    NORMAL("일반인"),
    MEDIUM("독서실러"),
    HIGH("도서관귀신");

    private final String label;

}
