package com.b3.ddarelro.domain.card.dto.request;

import lombok.*;

@Getter
public class CardModifyReq {

    private Long columnId;
    private String name;
    private String description;
    private String color;
}
