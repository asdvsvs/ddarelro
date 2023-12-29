package com.b3.ddarelro.domain.user.dto.response;

import lombok.Builder;

@Builder
public record UserRes(Long id, String email, String Username) {

}
