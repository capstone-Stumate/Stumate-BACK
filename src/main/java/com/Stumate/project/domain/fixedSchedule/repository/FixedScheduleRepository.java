package com.Stumate.project.domain.fixedSchedule.repository;

import com.Stumate.project.domain.fixedSchedule.entity.FixedSchedule;
import com.Stumate.project.domain.fixedSchedule.enums.DayOfWeekType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FixedScheduleRepository extends JpaRepository<FixedSchedule, Long> {
    List<FixedSchedule> findAllByUserId(Long userId);
    List<FixedSchedule> findAllByUserIdAndDayOfWeek(Long userId, DayOfWeekType dayOfWeek);
}
