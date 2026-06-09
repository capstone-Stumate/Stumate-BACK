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

    @GetMapping
    public ResponseEntity<List<FixedScheduleResDTO.ScheduleInfo>> getSchedules(@PathVariable Long userId) {
        return ResponseEntity.ok(fixedScheduleService.getSchedules(userId));
    }

    @PostMapping
    public ResponseEntity<List<FixedScheduleResDTO.ScheduleInfo>> createSchedule(
            @PathVariable Long userId,
            @Valid @RequestBody FixedScheduleReqDTO.ScheduleCreate request) {
        return ResponseEntity.ok(fixedScheduleService.createSchedule(userId, request));
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long userId,
            @PathVariable Long scheduleId) {
        fixedScheduleService.deleteSchedule(userId, scheduleId);
        return ResponseEntity.noContent().build();
    }
}