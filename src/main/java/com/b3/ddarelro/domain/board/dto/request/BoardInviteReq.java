package com.b3.ddarelro.domain.board.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardInviteReq {

    @NotBlank
    private Long userId;

    @Builder
    private BoardInviteReq(Long userId) {
        this.userId = userId;
    }

}
