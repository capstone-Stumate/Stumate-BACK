package com.Stumate.project.domain.userSubject.controller;

import com.Stumate.project.domain.userSubject.dto.UserSubjectReqDTO;
import com.Stumate.project.domain.userSubject.dto.UserSubjectResDTO;
import com.Stumate.project.domain.userSubject.service.UserSubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/subjects")
@RequiredArgsConstructor
public class UserSubjectController {

    private final UserSubjectService userSubjectService;

    // 과목 목록 조회
    @GetMapping
    public ResponseEntity<List<UserSubjectResDTO.Info>> getSubjects(@PathVariable Long userId) {
        return ResponseEntity.ok(userSubjectService.getSubjects(userId));
    }

    // 과목 추가
    @PostMapping
    public ResponseEntity<UserSubjectResDTO.Info> addSubject(
            @PathVariable Long userId,
            @Valid @RequestBody UserSubjectReqDTO.Create request) {
        return ResponseEntity.ok(userSubjectService.addSubject(userId, request));
    }

    // 과목 삭제
    @DeleteMapping("/{userSubjectId}")
    public ResponseEntity<Void> deleteSubject(
            @PathVariable Long userId,
            @PathVariable Long userSubjectId) {
        userSubjectService.deleteSubject(userId, userSubjectId);
        return ResponseEntity.noContent().build();
    }
}