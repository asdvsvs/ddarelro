package com.b3.ddarelro.domain.card.dto.response;

import com.b3.ddarelro.domain.card.entity.*;
import java.time.*;
import lombok.*;

@Builder
public record CardModifyRes(
    Long id,
    String name,
    String description,
    String color,
    LocalDate dueDate,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt,
    Long priority

) {

    public static CardModifyRes formWith(Card card) {
        return CardModifyRes.builder()
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
