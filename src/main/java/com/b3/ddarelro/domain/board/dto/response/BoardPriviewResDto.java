package com.b3.ddarelro.domain.board.dto.response;

import com.b3.ddarelro.domain.board.entity.Board;
import com.b3.ddarelro.domain.board.entity.Color;

public class BoardPriviewResDto {

    private Long id;
    private String name;
    private Color color;

    public BoardPriviewResDto(Board board){
        this.id = board.getId();
        this.name = board.getName();
        this.color = board.getColor();
    }

}
