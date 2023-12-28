package com.b3.ddarelro.domain.card.dto.request;

public record CardModifyReq(

    Long columnId,
    String name,
    String description,
    String color

) {

}
