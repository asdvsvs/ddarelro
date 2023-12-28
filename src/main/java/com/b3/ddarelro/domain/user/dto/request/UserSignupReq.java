package com.b3.ddarelro.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserSignupReq(
    @Pattern(
        regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}$",
        message = "적절한 이메일 형식이 아닙니다."
    )
    String email,

    @NotBlank( message = "유저명을 입력해주세요")
    String username,

    @Pattern(
        regexp = "^[a-zA-Z0-9]{4,15}$",
        message = "비밀번호는 영문자, 숫자를 포함한 8글자 이상 15글자 이하입니다."
    )
    String password,

    @Pattern(
        regexp = "^[a-zA-Z0-9]{4,15}$",
        message = "비밀번호는 영문자, 숫자를 포함한 8글자 이상 15글자 이하입니다."
    )
    String passwordCheck) {

}
