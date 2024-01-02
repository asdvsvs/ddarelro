package com.b3.ddarelro.domain.card.dto.response;

import com.b3.ddarelro.domain.card.entity.*;
import java.time.*;
import lombok.*;

@Builder
public record CardMoveRes(
    Long id,
    String name,
    String description,
    String color,
    LocalDate dueDate,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt,
    Long priority

) {

    public static CardMoveRes formWith(Card card) {
        return CardMoveRes.builder()
            .id(card.getId())
            .name(card.getName())
            .description(card.getDescription())
            .color(card.getColor())
            .dueDate(card.getDueDate())
            .createdAt(card.getCreatedAt())
            .modifiedAt(card.getModifiedAt())
            .priority(card.getPriority())
            .build();
    }
}
