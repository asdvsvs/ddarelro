package com.b3.ddarelro.domain.card.dto.response;

import com.b3.ddarelro.domain.card.entity.*;
import com.b3.ddarelro.domain.worker.*;
import java.time.*;
import java.util.*;
import lombok.*;

@Builder
public record CardRes(
    Long id,
    String name,
    String description,
    String color,
    LocalDate dueDate,
    List<WorkersRes> workers,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt,
    Long priority

) {

    public static CardRes formWith(Card card, List<WorkersRes> workersRes) {
        return CardRes.builder()
            .id(card.getId())
            .name(card.getName())
            .description(card.getDescription())
            .color(card.getColor())
            .dueDate(card.getDueDate())
            .workers(workersRes)
            .createdAt(card.getCreatedAt())
            .modifiedAt(card.getModifiedAt())
            .priority(card.getPriority())
            .build();
    }
}
