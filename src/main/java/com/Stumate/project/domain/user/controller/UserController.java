package com.Stumate.project.domain.user.controller;

import com.Stumate.project.domain.user.converter.UserConverter;
import com.Stumate.project.domain.user.dto.UserReqDTO;
import com.Stumate.project.domain.user.entity.User;
import com.Stumate.project.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserReqDTO.SignUp request) {
        User user = userService.signup(request);
        return ResponseEntity.ok(UserConverter.toSignupResult(user));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody UserReqDTO.Login request) {
        return ResponseEntity.ok("로그인 성공");
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok("로그아웃 성공");
    }
}