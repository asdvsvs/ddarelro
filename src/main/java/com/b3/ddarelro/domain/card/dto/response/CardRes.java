package com.b3.ddarelro.domain.card.dto.response;

import com.b3.ddarelro.domain.card.entity.*;
import java.time.*;
import lombok.*;

@Builder
public record CardRes(
    String name,
    String description,
    String color,
    LocalDate dueDate,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt

) {

    public static CardRes formWith(Card card) {
        return CardRes.builder()
            .name(card.getName())
//            .username(card.getUser().getUsername())
            .description(card.getDescription())
            .color(card.getColor())
            .dueDate(card.getDueDate())
            .createdAt(card.getCreatedAt())
            .modifiedAt(card.getModifiedAt())
            .build();
    }
}
