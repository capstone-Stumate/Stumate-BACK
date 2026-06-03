package com.Stumate.project.domain.user.converter;

import com.Stumate.project.domain.user.dto.UserResDTO;
import com.Stumate.project.domain.user.entity.User;

public class UserConverter {

    public static UserResDTO.Info toInfo(User user) {
        return UserResDTO.Info.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .name(user.getName())
                .planLevel(user.getPlanLevel())
                .build();
    }

    public static UserResDTO.Simple toSimple(User user) {
        return UserResDTO.Simple.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .build();
    }

    // 추가!
    public static UserResDTO.Info toSignupResult(User user) {
        return toInfo(user);
    }
}
