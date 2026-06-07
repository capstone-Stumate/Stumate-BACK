package com.Stumate.project.domain.studySession.controller;

import com.Stumate.project.domain.studySession.dto.StudySessionReqDTO;
import com.Stumate.project.domain.studySession.dto.StudySessionResDTO;
import com.Stumate.project.domain.studySession.service.StudySessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/users/{userId}/sessions")
@RequiredArgsConstructor
public class StudySessionController {

    private final StudySessionService studySessionService;

    // 타이머 시작
    @PostMapping("/start")
    public ResponseEntity<StudySessionResDTO.Info> startSession(
            @PathVariable Long userId,
            @Valid @RequestBody StudySessionReqDTO.Start request) {
        return ResponseEntity.ok(studySessionService.startSession(userId, request));
    }

    // 타이머 종료
    @PatchMapping("/{sessionId}/finish")
    public ResponseEntity<StudySessionResDTO.Info> finishSession(
            @PathVariable Long userId,
            @PathVariable Long sessionId,
            @Valid @RequestBody StudySessionReqDTO.Finish request) {
        return ResponseEntity.ok(studySessionService.finishSession(userId, sessionId, request));
    }

    // 주간 공부 시간 조회 (추가!)
    @GetMapping("/weekly-stats")
    public ResponseEntity<StudySessionResDTO.WeeklyStats> getWeeklyStats(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime weekStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime weekEnd) {
        return ResponseEntity.ok(studySessionService.getWeeklyStats(userId, weekStart, weekEnd));
    }
}