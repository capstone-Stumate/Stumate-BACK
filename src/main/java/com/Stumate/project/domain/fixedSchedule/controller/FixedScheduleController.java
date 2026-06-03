package com.Stumate.project.domain.fixedSchedule.controller;

import com.Stumate.project.domain.fixedSchedule.dto.FixedScheduleReqDTO;
import com.Stumate.project.domain.fixedSchedule.dto.FixedScheduleResDTO;
import com.Stumate.project.domain.fixedSchedule.service.FixedScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/schedules")
@RequiredArgsConstructor
public class FixedScheduleController {

    private final FixedScheduleService fixedScheduleService;

    // 고정 일정 목록 조회
    @GetMapping
    public ResponseEntity<List<FixedScheduleResDTO.Info>> getSchedules(@PathVariable Long userId) {
        return ResponseEntity.ok(fixedScheduleService.getSchedules(userId));
    }

    // 고정 일정 추가
    @PostMapping
    public ResponseEntity<FixedScheduleResDTO.Info> createSchedule(
            @PathVariable Long userId,
            @Valid @RequestBody FixedScheduleReqDTO.Create request) {
        return ResponseEntity.ok(fixedScheduleService.createSchedule(userId, request));
    }

}
