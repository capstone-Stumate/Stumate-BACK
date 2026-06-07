package com.Stumate.project.domain.user.service;

import com.Stumate.project.domain.user.dto.UserReqDTO;
import com.Stumate.project.domain.user.entity.User;
import com.Stumate.project.domain.user.repository.UserRepository;
import com.Stumate.project.global.exception.GlobalException;
import com.Stumate.project.global.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User signup(UserReqDTO.SignUp request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new GlobalException(ErrorCode.DUPLICATE_USERNAME);
        }
        User user = User.builder()
                .username(request.getUsername())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .planLevel(request.getPlanLevel())
                .build();
        return userRepository.save(user);
    }
}