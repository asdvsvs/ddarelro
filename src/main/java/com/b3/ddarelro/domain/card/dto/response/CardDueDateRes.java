package com.b3.ddarelro.domain.card.dto.response;

import com.b3.ddarelro.domain.card.entity.*;
import java.time.*;
import lombok.*;

@Builder
public record CardDueDateRes(
    String name,
    String description,
    String color,
    LocalDate dueDate,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt

) {

    public static CardDueDateRes formWith(Card card, LocalDate dueDate) {
        return CardDueDateRes.builder()
            .name(card.getName())
            //.username
            .description(card.getDescription())
            .color(card.getColor())
            .dueDate(dueDate)
            .createdAt(card.getCreatedAt())
            .modifiedAt(card.getModifiedAt())
            .build();
    }
}
