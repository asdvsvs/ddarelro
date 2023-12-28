package com.b3.ddarelro.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UsernameUpdateReq(@NotBlank(message = "수정할 username을 입력해주세요.") String username) {

}
