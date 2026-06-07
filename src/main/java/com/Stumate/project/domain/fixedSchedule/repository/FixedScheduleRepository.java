package com.Stumate.project.domain.fixedSchedule.repository;

import com.Stumate.project.domain.fixedSchedule.entity.FixedSchedule;
import com.Stumate.project.domain.fixedSchedule.enums.DayOfWeekType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FixedScheduleRepository extends JpaRepository<FixedSchedule, Long> {

    // 삭제된 것 제외하고 조회 (수정!)
    List<FixedSchedule> findAllByUserIdAndDeletedAtIsNull(Long userId);
    List<FixedSchedule> findAllByUserIdAndDayOfWeekAndDeletedAtIsNull(Long userId, DayOfWeekType dayOfWeek);
}