package com.Stumate.project.domain.fixedSchedule.converter;

import com.Stumate.project.domain.fixedSchedule.dto.FixedScheduleResDTO;
import com.Stumate.project.domain.fixedSchedule.entity.FixedSchedule;

import java.util.List;

public class FixedScheduleConverter {

    public static FixedScheduleResDTO.ScheduleInfo toInfo(FixedSchedule schedule) {
        return FixedScheduleResDTO.ScheduleInfo.builder()
                .scheduleId(schedule.getScheduleId())
                .scheduleName(schedule.getScheduleName())
                .days(List.of(schedule.getDayOfWeek()))
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .build();
    }
}