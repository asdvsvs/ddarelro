package com.b3.ddarelro.domain.board.dto.response;

import com.b3.ddarelro.domain.board.entity.Board;
import com.b3.ddarelro.domain.board.entity.Color;
import lombok.Getter;

@Getter
public class BoardUpdateResDto {

    private Color color;
    private String name;
    private String description;
    private Boolean deleted;

    public BoardUpdateResDto(Board board){
        this.color = board.getColor();
        this.name = board.getName();
        this.description = board.getDescription();
        this.deleted = board.getDeleted();
    }

}
