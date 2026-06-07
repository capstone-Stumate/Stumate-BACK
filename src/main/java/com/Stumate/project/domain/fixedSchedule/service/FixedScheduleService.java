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

    // 사용자 고정 일정 전체 조회 (삭제된 것 제외)
    public List<FixedScheduleResDTO.Info> getSchedules(Long userId) {
        return fixedScheduleRepository.findAllByUserIdAndDeletedAtIsNull(userId).stream()
                .map(FixedScheduleConverter::toInfo)
                .collect(Collectors.toList());
    }

    // 고정 일정 추가
    @Transactional
    public FixedScheduleResDTO.Info createSchedule(Long userId, FixedScheduleReqDTO.Create request) {
        FixedSchedule schedule = FixedScheduleConverter.toEntity(userId, request);
        return FixedScheduleConverter.toInfo(fixedScheduleRepository.save(schedule));
    }

    // 고정 일정 삭제 (soft delete로 수정!)
    @Transactional
    public void deleteSchedule(Long userId, Long scheduleId) {
        FixedSchedule schedule = fixedScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new GlobalException(ErrorCode.SCHEDULE_NOT_FOUND));
        schedule.delete(); // hard delete → soft delete로 수정
    }
}