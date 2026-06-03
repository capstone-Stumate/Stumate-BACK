package com.Stumate.project.domain.fixedSchedule.dto;

import com.Stumate.project.domain.fixedSchedule.enums.DayOfWeekType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalTime;

public class FixedScheduleReqDTO {

    @Getter
    public static class Create {

        @NotBlank(message = "일정 이름을 입력해주세요.")
        private String scheduleName;

        @NotNull(message = "요일을 선택해주세요.")
        private DayOfWeekType dayOfWeek;

        @NotNull(message = "시작 시간을 입력해주세요.")
        private LocalTime startTime;

        @NotNull(message = "종료 시간을 입력해주세요.")
        private LocalTime endTime;
    }
}
