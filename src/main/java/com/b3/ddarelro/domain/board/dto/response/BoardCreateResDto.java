package com.b3.ddarelro.domain.board.dto.response;


import com.b3.ddarelro.domain.board.entity.Board;
import com.b3.ddarelro.domain.board.entity.Color;
import lombok.Getter;

@Getter
public class BoardCreateResDto {

    private Long id;
    private String description;
    private String name;
    private Color color;

    public BoardCreateResDto(Board board){
        this.id = board.getId();
        this.description = board.getDescription();
        this.name = board.getName();
        this.color = board.getColor();
    }

}
