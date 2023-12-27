package com.b3.ddarelro.domain.card.dto.request;

import lombok.*;

@Getter
public class CardCreateReqDto {

    private Long columnId;
    private String title;
    private String content;
    private String color;

}
