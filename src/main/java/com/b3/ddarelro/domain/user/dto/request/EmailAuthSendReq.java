package com.b3.ddarelro.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record EmailAuthSendReq(@NotBlank(message = "Email을 입력해주세요") String email) {

}
