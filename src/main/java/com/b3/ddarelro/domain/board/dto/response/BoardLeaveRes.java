package com.b3.ddarelro.domain.board.dto.response;

import lombok.Getter;

@Getter
public class BoardLeaveRes {

    String message;

    public BoardLeaveRes(String message){
        this.message = message;
    }
}
