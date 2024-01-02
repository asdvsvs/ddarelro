package com.b3.ddarelro.domain.user.dto.request;

import jakarta.validation.constraints.Pattern;

public record UserPasswordUpdateReq(
    @Pattern(
        regexp = "^[a-zA-Z0-9]{4,15}$",
        message = "비밀번호는 영문자, 숫자를 포함한 4글자 이상 15글자 이하입니다."
    )
    String password,

    @Pattern(
        regexp = "^[a-zA-Z0-9]{4,15}$",
        message = "비밀번호는 영문자, 숫자를 포함한 4글자 이상 15글자 이하입니다."
    )
    String passwordCheck) {

}
