package com.Stumate.project.domain.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlanLevel {
    EASY(1, "일반인"),
    NORMAL(2, "독서실러"),
    HARD(3, "도서관귀신");

    private final int value;
    private final String label;
}