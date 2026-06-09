package com.Stumate.project.domain.planner.controller;

import com.Stumate.project.domain.planner.dto.PlannerResDTO;
import com.Stumate.project.domain.planner.service.PlannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{userId}/planner")
@RequiredArgsConstructor
public class PlannerController {

    private final PlannerService plannerService;

    @GetMapping
    public ResponseEntity<PlannerResDTO.PlannerInfo> getPlanner(@PathVariable Long userId) {
        return ResponseEntity.ok(plannerService.getPlanner(userId));
    }
}