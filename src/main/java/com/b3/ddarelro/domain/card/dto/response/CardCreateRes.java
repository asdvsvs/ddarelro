package com.b3.ddarelro.domain.card.dto.response;

import com.b3.ddarelro.domain.card.entity.*;
import java.time.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardCreateRes {

    private String title;
    //private String username;
    private String description;
    private String color;
    private LocalDateTime createdAt;

    @Builder
    public CardCreateRes(String title, String description, String color, LocalDateTime createdAt) {
        this.title = title;
        this.description = description;
        this.color = color;
        this.createdAt = createdAt;
    }

    public static CardCreateRes formingWith(Card card) {
        return CardCreateRes.builder()
            .title(card.getName())
            //.username(card.getUser().getUsername())
            .description(card.getDescription())
            .color(card.getColor())
            .createdAt(card.getCreatedAt())
            .build();
    }

}
