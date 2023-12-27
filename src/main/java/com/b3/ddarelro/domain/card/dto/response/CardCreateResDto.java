package com.b3.ddarelro.domain.card.dto.response;

import com.b3.ddarelro.domain.card.entity.*;
import java.time.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardCreateResDto {

    private String title;
    //private String username;
    private String content;
    private String color;
    private LocalDateTime createdAt;

    @Builder
    public CardCreateResDto(String title, String content, String color, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.color = color;
        this.createdAt = createdAt;
    }

    public static CardCreateResDto formingWith(Card card) {
        return CardCreateResDto.builder()
            .title(card.getTitle())
            //.username(card.getUser().getUsername())
            .content(card.getContent())
            .color(card.getColor())
            .createdAt(card.getCreatedAt())
            .build();
    }

}
