package com.b3.ddarelro.domain.card.service;

import com.b3.ddarelro.domain.card.dto.request.*;
import com.b3.ddarelro.domain.card.dto.response.*;
import com.b3.ddarelro.domain.card.entity.*;
import com.b3.ddarelro.domain.card.repository.*;
import com.b3.ddarelro.domain.column.service.*;
import java.util.*;
import java.util.stream.*;
import lombok.*;
import org.springframework.stereotype.*;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final ColumnService columnService;

    public CardCreateRes createCard(CardCreateReq reqDto) {
//        columnService.findColumn(reqDto.getColumnId()).orElseThrow(() -> new GlobalException());
        Card newCard = Card.builder()
            .name(reqDto.getName())
            //.user(user)
            .description(reqDto.getDescription())
            .color(reqDto.getColor())
            .build();
        cardRepository.save(newCard);
        return CardCreateRes.formingWith(newCard);
    }

    public List<CardListRes> getCardList() {
//        Column column = columnService.findColumn(reqDto.getColumnId())
//            .orElseThrow(() -> new GlobalException());
        List<Card> cardList = cardRepository.findAllByOrderByCreatedAtDesc();
        return cardList.stream().map(card -> CardListRes.formWith(card))
            .collect(Collectors.toList());
    }
}
