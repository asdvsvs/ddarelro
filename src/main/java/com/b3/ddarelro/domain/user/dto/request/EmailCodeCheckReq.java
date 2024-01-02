package com.b3.ddarelro.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record EmailCodeCheckReq(
    @NotBlank(message = "Email을 입력해주세요")
    String email,
    @NotBlank(message = "인증 Code를 입력해주세요")
    String code
) {

}
