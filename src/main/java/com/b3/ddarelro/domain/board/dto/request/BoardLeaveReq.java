package com.b3.ddarelro.domain.board.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardLeaveReq {

    private Long userId; //팀장일경우 해당 userId에게 팀장권한을 부여해야한다.

    @Builder
    private BoardLeaveReq(Long userId) {

        this.userId = userId;
    }

}
