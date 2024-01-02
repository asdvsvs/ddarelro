package com.b3.ddarelro.domain.card.dto.request;

public record CardMoveReq(
    Long columnId,
    Long anotherColumnId,
    Long spot
) {

}
