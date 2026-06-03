package com.Stumate.project.domain.fixedSchedule.dto;

import com.Stumate.project.domain.fixedSchedule.enums.DayOfWeekType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

public class FixedScheduleResDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Info {
        private Long scheduleId;
        private String scheduleName;
        private DayOfWeekType dayOfWeek;
        private LocalTime startTime;
        private LocalTime endTime;
    }
}
