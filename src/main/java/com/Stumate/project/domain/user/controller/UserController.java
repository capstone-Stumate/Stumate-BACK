package com.Stumate.project.domain.user.controller;

import com.Stumate.project.domain.user.converter.UserConverter;
import com.Stumate.project.domain.user.dto.UserReqDTO;
import com.Stumate.project.domain.user.dto.UserResDTO;
import com.Stumate.project.domain.user.entity.User;
import com.Stumate.project.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResDTO.UserInfo> signup(@RequestBody UserReqDTO.SignUp request) {
        User user = userService.signup(request);
        return ResponseEntity.ok(UserConverter.toInfo(user));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<UserResDTO.UserInfo> login(@RequestBody UserReqDTO.Login request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok("로그아웃 성공");
    }

    @PatchMapping("/{userId}/plan")
    public ResponseEntity<UserResDTO.UserInfo> updatePlanInfo(
            @PathVariable Long userId,
            @RequestBody UserReqDTO.UpdatePlanInfo request) {
        return ResponseEntity.ok(userService.updatePlanInfo(userId, request));
    }
}