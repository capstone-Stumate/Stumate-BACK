package com.Stumate.project.domain.fixedSchedule.converter;

import com.Stumate.project.domain.fixedSchedule.dto.FixedScheduleReqDTO;
import com.Stumate.project.domain.fixedSchedule.dto.FixedScheduleResDTO;
import com.Stumate.project.domain.fixedSchedule.entity.FixedSchedule;

public class FixedScheduleConverter {

    public static FixedSchedule toEntity(Long userId, FixedScheduleReqDTO.Create request) {
        return FixedSchedule.builder()
                .userId(userId)
                .scheduleName(request.getScheduleName())
                .dayOfWeek(request.getDayOfWeek())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();
    }

    public static FixedScheduleResDTO.Info toInfo(FixedSchedule schedule) {
        return FixedScheduleResDTO.Info.builder()
                .scheduleId(schedule.getScheduleId())
                .scheduleName(schedule.getScheduleName())
                .dayOfWeek(schedule.getDayOfWeek())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .build();
    }
}
