package com.b3.ddarelro.domain.card.service;

import com.b3.ddarelro.domain.card.dto.request.*;
import com.b3.ddarelro.domain.card.dto.response.*;
import com.b3.ddarelro.domain.card.entity.*;
import com.b3.ddarelro.domain.card.exception.*;
import com.b3.ddarelro.domain.card.repository.*;
import com.b3.ddarelro.domain.column.entity.*;
import com.b3.ddarelro.global.exception.*;
import java.util.*;
import java.util.stream.*;
import lombok.*;
import org.springframework.stereotype.*;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
//    private final ColumnService columnService;
//    private final UserService userService;

    public CardCreateRes createCard(CardCreateReq req) {
        //TODO 컬럼 deleted 확인
//        columnService.findColumn(reqDto.getColumnId()).orElseThrow(() -> new GlobalException());
        Card newCard = Card.builder()
            .name(req.name())
            //.user(user)
            .description(req.description())
            .color(req.color())
            .build();
        cardRepository.save(newCard);
        return CardCreateRes.formingWith(newCard);
    }

    public List<CardListRes> getCardList() {
        //TODO 컬럼 deleted 확인
//        Column column = columnService.findColumn(reqDto.getColumnId())
//            .orElseThrow(() -> new GlobalException());
        List<Card> cardList = cardRepository.findAllByOrderByCreatedAtDesc();
        return cardList.stream().map(card -> CardListRes.formWith(card))
            .collect(Collectors.toList());
    }

    public CardRes getCard(Long cardId) {
        //TODO 컬럼 deleted 확인
//        Column column = columnService.findColumn(reqDto.getColumnId())
//            .orElseThrow(() -> new GlobalException());
        Card card = findCard(cardId);

        return CardRes.formWith(card);
    }

    public CardModifyRes modifyCard(Long cardId, CardModifyReq req) {
        //TODO 컬럼 deleted 확인
        Card card = findCard(cardId);
        //findUser(user, card);
        card.modifyCard(req);
        return CardModifyRes.formWith(card);
    }

    //TODO : 마감일 지정
//    public CardDueDateRes setDueDateCard(Long cardId, CardDueDateReq req) {
//        Card card = findCard(cardId);
//        card.
//
//    }

    public CardDeleteRes deleteCard(Long cardId, CardDeleteReq req) {
        //TODO 컬럼 deleted 확인
        Column column = columnService.findColum(req);
        if (column.getDeleted()) {
            throw new GlobalException(ColumnErrorCode.NOT_FOUND);
        }

        Card card = getUserCard(cardId);
        card.deleteCard();
        return CardDeleteRes.builder().msg("카드가 삭제 됬어요!").build();
    }

    private Card getUserCard(Long cardId) {
        Card card = findCard(cardId);
//        if (!card.getUser().getId().equals(userId)) {
//            throw new GlobalException(CardErrorCode.INVALID_USER);
//        }
        return card;
    }

    public Card findCard(Long cardId) {
        return cardRepository.findById(cardId)
            .orElseThrow(() -> new GlobalException(CardErrorCode.NOT_FOUND));
    }
}
