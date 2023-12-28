package com.b3.ddarelro.domain.card.dto.response;

import com.b3.ddarelro.domain.card.entity.Card;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardRes {

    private String name;
    //    private String nickname;
    private String description;
    private String color;
    private LocalDateTime createdAt;

    @Builder
    public CardRes(String name, String description, String color,
        LocalDateTime createdAt) {
        this.name = name;
//        this.nickname = nickname;
        this.description = description;
        this.color = color;
        this.createdAt = createdAt;
    }

    public static CardRes formWith(Card card) {
        return CardRes.builder()
            .name(card.getName())
//            .nickname(card.getUser().getUsername())
            .description(card.getDescription())
            .color(card.getColor())
            .createdAt(card.getCreatedAt())
            .build();
    }
}
