package com.b3.ddarelro.domain.card.dto.response;

import com.b3.ddarelro.domain.card.entity.*;
import java.time.*;
import lombok.*;

@Builder
public record CardDueDateRes(
    Long id,
    String name,
    String username,
    String description,
    String color,
    LocalDate dueDate,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt,
    Long priority

) {

    public static CardDueDateRes formWith(Card card, LocalDate dueDate) {
        return CardDueDateRes.builder()
            .id(card.getId())
            .name(card.getName())
            .username(card.getUser().getUsername())
            .description(card.getDescription())
            .color(card.getColor())
            .dueDate(dueDate)
            .createdAt(card.getCreatedAt())
            .modifiedAt(card.getModifiedAt())
            .priority(card.getPriority())
            .build();
    }
}
