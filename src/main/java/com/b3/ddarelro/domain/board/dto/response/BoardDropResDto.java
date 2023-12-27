package com.b3.ddarelro.domain.board.dto.response;

import lombok.Getter;

@Getter
public class BoardDropResDto {

    String message;

    public BoardDropResDto(String message){
        this.message = message;
    }
}
