package com.b3.ddarelro.domain.board.dto.response;

import lombok.Getter;

@Getter
public class BoardInviteResDto {
    String message;

    public BoardInviteResDto(String message){
        this.message = message;
    }

}
