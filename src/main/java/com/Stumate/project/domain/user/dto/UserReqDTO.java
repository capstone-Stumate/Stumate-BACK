package com.Stumate.project.domain.user.dto;

import lombok.Getter;

public class UserReqDTO {

    @Getter
    public static class SignUp {
        private String username;
        private String password;
        private String name;
        private Integer planLevel;
    }

    @Getter
    public static class Login {
        private String username;
        private String password;
    }

    @Getter
    public static class UpdatePlanLevel {
        private Integer planLevel;
    }
}
