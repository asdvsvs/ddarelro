package com.b3.ddarelro.domain.card.dto.response;

import com.b3.ddarelro.domain.card.entity.*;
import java.time.*;
import lombok.*;

@Builder
public record CardCreateRes(
    String name,
    String description,
    String color,
    LocalDate dueDate,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt

) {

    public static CardCreateRes formingWith(Card card) {
        return CardCreateRes.builder()
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
