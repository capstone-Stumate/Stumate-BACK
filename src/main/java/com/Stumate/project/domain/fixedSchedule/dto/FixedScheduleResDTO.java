package com.Stumate.project.domain.fixedSchedule.dto;

import com.Stumate.project.domain.fixedSchedule.enums.DayOfWeekType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

public class FixedScheduleResDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ScheduleInfo {
        private Long scheduleId;
        private String scheduleName;
        private List<DayOfWeekType> days;
        private LocalTime startTime;
        private LocalTime endTime;
    }
}