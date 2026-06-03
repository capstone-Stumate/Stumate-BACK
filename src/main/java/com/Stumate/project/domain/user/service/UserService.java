package com.Stumate.project.domain.user.service;

import com.Stumate.project.domain.user.dto.UserReqDTO;
import com.Stumate.project.domain.user.entity.User;
import com.Stumate.project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User signup(UserReqDTO.SignUp request) {
        User user = User.builder()
                .username(request.getUsername())
                .name(request.getName())
                .password(request.getPassword())
                .planLevel(request.getPlanLevel())
                .build();
        return userRepository.save(user);
    }
}