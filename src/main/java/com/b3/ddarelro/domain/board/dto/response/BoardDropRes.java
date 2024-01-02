package com.b3.ddarelro.domain.board.dto.response;

import lombok.Getter;

@Getter
public class BoardDropRes {

    String message;

    public BoardDropRes(String message){
        this.message = message;
    }
}
