package com.b3.ddarelro.domain.card.dto.response;

import com.b3.ddarelro.domain.card.entity.Card;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardModifyRes {

    private String name;
    //private String nickname;
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
            //.nickname
            .description(card.getDescription())
            .color(card.getColor())
            .createdAt(card.getCreatedAt())
            .modifiedAt(card.getModifiedAt())
            .build();
    }
}
