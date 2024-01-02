package com.b3.ddarelro.domain.card.dto.request;

public record CardDueDateReq(
    Long columnId,
    Integer year,
    Integer month,
    Integer day

) {

}
