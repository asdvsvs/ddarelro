package com.b3.ddarelro.domain.card.dto.response;

import com.b3.ddarelro.domain.card.entity.*;
import java.time.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardModifyRes {

    private String name;
    //private String username;
    private String description;
    private String color;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    public CardModifyRes(String name, String description, String color, LocalDateTime createdAt,
        LocalDateTime modifiedAt) {
        this.name = name;
        this.description = description;
        this.color = color;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static CardModifyRes formWith(Card card) {
        return CardModifyRes.builder()
            .name(card.getName())
            //.username
            .description(card.getDescription())
            .color(card.getColor())
            .createdAt(card.getCreatedAt())
            .modifiedAt(card.getModifiedAt())
            .build();
    }
}
