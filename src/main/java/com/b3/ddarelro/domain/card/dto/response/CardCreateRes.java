package com.b3.ddarelro.domain.card.dto.response;

import com.b3.ddarelro.domain.card.entity.*;
import java.time.*;
import lombok.*;

@Builder
public record CardCreateRes(
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

    public static CardCreateRes formingWith(Card card, Long priority) {
        return CardCreateRes.builder()
            .id(card.getId())
            .name(card.getName())
            .username(card.getUser().getUsername())
            .description(card.getDescription())
            .color(card.getColor())
            .dueDate(card.getDueDate())
            .createdAt(card.getCreatedAt())
            .modifiedAt(card.getModifiedAt())
            .priority(priority)
            .build();
    }

}
