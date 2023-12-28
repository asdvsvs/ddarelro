package com.b3.ddarelro.domain.card.dto.response;

import com.b3.ddarelro.domain.card.entity.*;
import java.time.*;
import lombok.*;

@Builder
public record CardModifyRes(
    String name,
    String description,
    String color,
    LocalDate dueDate,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt

) {

    public static CardModifyRes formWith(Card card) {
        return CardModifyRes.builder()
            .name(card.getName())
            //.username
            .description(card.getDescription())
            .color(card.getColor())
            .dueDate(card.getDueDate())
            .createdAt(card.getCreatedAt())
            .modifiedAt(card.getModifiedAt())
            .build();
    }
}
