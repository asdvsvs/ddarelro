package com.b3.ddarelro.domain.card.dto.response;

import com.b3.ddarelro.domain.card.entity.Card;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardCreateRes {

    private String title;
    //private String nickname;
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
            //.nickname(card.getUser().getUsername())
            .description(card.getDescription())
            .color(card.getColor())
            .createdAt(card.getCreatedAt())
            .build();
    }

}
