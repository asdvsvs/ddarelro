package com.b3.ddarelro.domain.board.dto.request;

import com.b3.ddarelro.domain.board.entity.Color;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardUpdateReqDto {
    private Color color;
    private String name;
    private String description;
    private Boolean deleted;

    @Builder
    private BoardUpdateReqDto(Color color, String name, String description){
        this.color = color;
        this.deleted = false;
        this.name = name;
        this.description = description;
    }
}
