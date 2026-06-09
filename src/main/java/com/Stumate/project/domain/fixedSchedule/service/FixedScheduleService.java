package com.Stumate.project.domain.fixedSchedule.service;

import com.Stumate.project.domain.fixedSchedule.converter.FixedScheduleConverter;
import com.Stumate.project.domain.fixedSchedule.dto.FixedScheduleReqDTO;
import com.Stumate.project.domain.fixedSchedule.dto.FixedScheduleResDTO;
import com.Stumate.project.domain.fixedSchedule.entity.FixedSchedule;
import com.Stumate.project.domain.fixedSchedule.repository.FixedScheduleRepository;
import com.Stumate.project.global.exception.GlobalException;
import com.Stumate.project.global.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FixedScheduleService {

    private final FixedScheduleRepository fixedScheduleRepository;

    public List<FixedScheduleResDTO.ScheduleInfo> getSchedules(Long userId) {
        return fixedScheduleRepository.findAllByUserIdAndDeletedAtIsNull(userId).stream()
                .map(FixedScheduleConverter::toInfo)
                .collect(Collectors.toList());
    }

    // 여러 요일을 각각 저장
    @Transactional
    public List<FixedScheduleResDTO.ScheduleInfo> createSchedule(Long userId, FixedScheduleReqDTO.ScheduleCreate request) {
        return request.getDays().stream()
                .map(day -> {
                    FixedSchedule schedule = FixedSchedule.builder()
                            .userId(userId)
                            .scheduleName(request.getScheduleName())
                            .dayOfWeek(day)
                            .startTime(request.getStartTime())
                            .endTime(request.getEndTime())
                            .build();
                    return FixedScheduleConverter.toInfo(fixedScheduleRepository.save(schedule));
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteSchedule(Long userId, Long scheduleId) {
        FixedSchedule schedule = fixedScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new GlobalException(ErrorCode.SCHEDULE_NOT_FOUND));
        schedule.delete();
    }
}