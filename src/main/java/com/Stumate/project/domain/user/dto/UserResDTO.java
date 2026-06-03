package com.Stumate.project.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class UserResDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Info {
        private Long userId;
        private String username;
        private String name;
        private Integer planLevel;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Simple {
        private Long userId;
        private String name;
    }
}
