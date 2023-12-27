package com.b3.ddarelro.domain.card.service;

import com.b3.ddarelro.domain.card.dto.request.*;
import com.b3.ddarelro.domain.card.dto.response.*;
import com.b3.ddarelro.domain.card.entity.*;
import com.b3.ddarelro.domain.card.exception.*;
import com.b3.ddarelro.domain.card.repository.*;
import com.b3.ddarelro.global.exception.*;
import java.util.*;
import java.util.stream.*;
import lombok.*;
import org.springframework.stereotype.*;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
//    private final UserService userService;

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

    public CardRes getCard(Long cardId) {
//        Column column = columnService.findColumn(reqDto.getColumnId())
//            .orElseThrow(() -> new GlobalException());
        Card card = findCard(cardId);

        return CardRes.formWith(card);
    }

    public CardModifyRes modifyCard(Long cardId, CardModifyReq reqDto) {
        Card card = findCard(cardId);
        //findUser(user, card);
        card.modifyCard(reqDto);
        return CardModifyRes.formWith(card);
    }

    public Card findCard(Long cardId) {
        return cardRepository.findById(cardId)
            .orElseThrow(() -> new GlobalException(CardErrorCode.NOT_FOUND));
    }
}
