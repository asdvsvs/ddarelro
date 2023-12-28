package com.b3.ddarelro.domain.card.dto.response;

import com.b3.ddarelro.domain.card.entity.*;
import java.time.*;
import lombok.*;

@Builder
public record CardListRes(
    String name,
    String description,
    String color,
    LocalDateTime createdAt

) {

    public static CardListRes formWith(Card card) {
        return CardListRes.builder()
            .name(card.getName())
            //.username(card.getUser().getUsername())
            .description(card.getDescription())
            .color(card.getColor())
            .createdAt(card.getCreatedAt())
            .build();
    }
}
