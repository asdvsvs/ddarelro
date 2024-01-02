package com.b3.ddarelro.domain.board.dto.request;

import com.b3.ddarelro.domain.board.entity.Color;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardUpdateReq{
    @NotBlank
    private Color color;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private Boolean deleted;

    @Builder
    private BoardUpdateReq(Color color, String name, String description){
        this.color = color;
        this.deleted = false;
        this.name = name;
        this.description = description;
    }
}
