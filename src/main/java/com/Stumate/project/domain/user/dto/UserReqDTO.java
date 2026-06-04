package com.Stumate.project.domain.user.dto;

import com.Stumate.project.domain.user.enums.PlanLevel;
import lombok.Getter;

public class UserReqDTO {

    @Getter
    public static class SignUp {
        private String username;
        private String password;
        private String name;
        private PlanLevel planLevel;
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
