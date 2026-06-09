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

    @GetMapping
    public ResponseEntity<List<UserSubjectResDTO.SubjectInfo>> getSubjects(@PathVariable Long userId) {
        return ResponseEntity.ok(userSubjectService.getSubjects(userId));
    }

    @PostMapping
    public ResponseEntity<UserSubjectResDTO.SubjectInfo> addSubject(
            @PathVariable Long userId,
            @Valid @RequestBody UserSubjectReqDTO.UserSubjectCreate request) {
        return ResponseEntity.ok(userSubjectService.addSubject(userId, request));
    }

    @DeleteMapping("/{userSubjectId}")
    public ResponseEntity<Void> deleteSubject(
            @PathVariable Long userId,
            @PathVariable Long userSubjectId) {
        userSubjectService.deleteSubject(userId, userSubjectId);
        return ResponseEntity.noContent().build();
    }
}