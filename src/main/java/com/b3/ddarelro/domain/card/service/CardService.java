package com.b3.ddarelro.domain.card.service;

import com.b3.ddarelro.domain.card.dto.request.*;
import com.b3.ddarelro.domain.card.dto.response.*;
import com.b3.ddarelro.domain.card.entity.*;
import com.b3.ddarelro.domain.card.exception.*;
import com.b3.ddarelro.domain.card.repository.*;
import com.b3.ddarelro.domain.column.entity.*;
import com.b3.ddarelro.domain.column.exception.*;
import com.b3.ddarelro.domain.column.service.*;
import com.b3.ddarelro.global.exception.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final ColumnService columnService;
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

    @Transactional
    public CardModifyRes modifyCard(Long cardId, CardModifyReq req) {
        Long columnId = req.columnId();
        checkColumn(columnId);

        Card card = findCard(cardId);
        //findUser(user, card);
        card.modifyCard(req);
        return CardModifyRes.formWith(card);
    }

    @Transactional
    public CardDueDateRes setDueDateCard(Long cardId, CardDueDateReq req) {
        Long columnId = req.columnId();
        checkColumn(columnId);

        Card card = findCard(cardId);

        LocalDate dueDate = getDueDate(req);
        card.setDueDate(dueDate);
        return CardDueDateRes.formWith(card, dueDate);

    }

    public CardDeleteRes deleteCard(Long cardId, CardDeleteReq req) {
        Long columnId = req.columnId();
        checkColumn(columnId);

        Card card = getUserCard(cardId);
        card.deleteCard();
        return CardDeleteRes.builder().msg("카드가 삭제 됬어요!").build();
    }

    private static LocalDate getDueDate(CardDueDateReq req) {
        int year = req.dueDateY();
        int month = req.dueDateM();
        int day = req.dueDateD();
        LocalDate dueDate;
        dueDate = LocalDate.of(year, month, day);
        return dueDate;
    }

    private void checkColumn(Long columnId) {
        Column column = columnService.findColumn(columnId);
        if (column.getDeleted()) {
            throw new GlobalException(ColumnErrorCode.IS_DELETED);
        }
    }

    private Card getUserCard(Long cardId) {
        Card card = findCard(cardId);
        //TODO 사용자 확인
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
