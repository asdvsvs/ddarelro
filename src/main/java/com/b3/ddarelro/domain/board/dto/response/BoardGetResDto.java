package com.b3.ddarelro.domain.board.dto.response;

import com.b3.ddarelro.domain.board.entity.Board;
import com.b3.ddarelro.domain.board.entity.ColorEnum;
import lombok.Getter;

@Getter
public class BoardGetResDto {

    private Long id;
    private String description;
    private String name;
    private ColorEnum color;

    public BoardGetResDto(Board board){
        this.id = board.getId();
        this.description = board.getDescription();
        this.name = board.getName();
        this.color = board.getColor();
    }
}
