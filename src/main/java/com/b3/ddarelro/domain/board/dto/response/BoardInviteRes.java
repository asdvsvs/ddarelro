package com.b3.ddarelro.domain.board.dto.response;

import lombok.Getter;

@Getter
public class BoardInviteRes {
    String message;

    public BoardInviteRes(String message){
        this.message = message;
    }

}
