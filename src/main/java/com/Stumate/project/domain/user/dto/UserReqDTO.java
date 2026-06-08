package com.Stumate.project.domain.user.dto;

import com.Stumate.project.domain.user.enums.PlanLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class UserReqDTO {

    @Getter
    public static class SignUp {

        @NotBlank(message = "아이디를 입력해주세요.")
        private String username;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
        private String password;

        @NotBlank(message = "이름을 입력해주세요.")
        private String name;
    }

    @Getter
    public static class UpdatePlanInfo {
        private PlanLevel planLevel;
    }

    @Getter
    public static class Login {

        @NotBlank(message = "아이디를 입력해주세요.")
        private String username;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        private String password;
    }
}