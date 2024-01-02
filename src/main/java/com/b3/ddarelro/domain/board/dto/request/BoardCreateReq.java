package com.b3.ddarelro.domain.board.dto.request;

import com.b3.ddarelro.domain.board.entity.Color;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardCreateReq {

    @NotBlank
    private Color color;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private Boolean deleted;

    @Builder
    private BoardCreateReq(Color color, String name, String description){
        this.color = color;
        this.deleted = false;
        this.name = name;
        this.description = description;
    }
}
