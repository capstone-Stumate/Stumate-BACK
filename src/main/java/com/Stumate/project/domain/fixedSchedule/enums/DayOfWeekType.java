package com.Stumate.project.domain.fixedSchedule.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DayOfWeekType {
    MON(0, "월"),
    TUE(1, "화"),
    WED(2, "수"),
    THU(3, "목"),
    FRI(4, "금"),
    SAT(5, "토"),
    SUN(6, "일");

    private final int value;
    private final String label;
}
