package com.b3.ddarelro.domain.card.service;

import com.b3.ddarelro.domain.card.dto.request.*;
import com.b3.ddarelro.domain.card.dto.response.*;
import com.b3.ddarelro.domain.card.entity.*;
import com.b3.ddarelro.domain.card.exception.*;
import com.b3.ddarelro.domain.card.repository.*;
import com.b3.ddarelro.domain.column.service.*;
import com.b3.ddarelro.domain.comment.service.*;
import com.b3.ddarelro.domain.user.entity.*;
import com.b3.ddarelro.domain.user.service.*;
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
    private final UserService userService;
    private final CommentDeleteRestoreService commentDeleteRestoreService;

    @Transactional
    public CardCreateRes createCard(CardCreateReq req, User user) {
        userService.findUser(user.getId());
        Long columnId = req.columnId();
        columnService.findColumn(columnId);

        Long priority = cardRepository.countByColumnId(req.columnId()) + 1;

        Card newCard = Card.builder()
            .name(req.name())
            .user(user)
            .description(req.description())
            .color(req.color())
            .priority(priority)
            .build();
        cardRepository.save(newCard);
        return CardCreateRes.formingWith(newCard, priority);
    }

    public List<CardListRes> getCardList(CardListReq req, User user) {
        userService.findUser(user.getId());
        Long columnId = req.columnId();
        columnService.findColumn(columnId);

        List<Card> cardList = cardRepository.findAllByOrderByCreatedAtDesc();

        return cardList.stream().map(card -> CardListRes.formWith(card))
            .collect(Collectors.toList());
    }

    @Transactional
    public CardRes getCard(Long cardId, CardReq req, User user) {
        userService.findUser(user.getId());
        Long columnId = req.columnId();
        columnService.findColumn(columnId);

        Card card = findCard(cardId);

        return CardRes.formWith(card);
    }

    @Transactional
    public CardModifyRes modifyCard(Long cardId, CardModifyReq req, User user) {
        userService.findUser(user.getId());
        Long columnId = req.columnId();
        columnService.findColumn(columnId);

        Card card = findCard(cardId);

        card.modifyCard(req);
        return CardModifyRes.formWith(card);
    }

    @Transactional
    public CardDueDateRes setDueDateCard(Long cardId, CardDueDateReq req, User user) {
        userService.findUser(user.getId());
        Long columnId = req.columnId();
        columnService.findColumn(columnId);

        Card card = findCard(cardId);

        LocalDate dueDate = getDueDate(req);
        card.setDueDate(dueDate);
        return CardDueDateRes.formWith(card, dueDate);

    }

    @Transactional
    public CardDeleteRes deleteCard(Long cardId, CardDeleteReq req, User user) {
        userService.findUser(user.getId());
        Long columnId = req.columnId();
        columnService.findColumn(columnId);

        Card card = getUserCard(cardId, user);
        card.deleteCard();
        List<Card> cardList = cardRepository.findAllByOrderByCreatedAtDesc();
        List<Long> cardIdList = cardList.stream().map(Card::getId).toList();
        commentDeleteRestoreService.deleteAllComment(cardIdList);
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

    private Card getUserCard(Long cardId, User user) {
        Card card = findCard(cardId);
        if (!card.getUser().getId().equals(user.getId())) {
            throw new GlobalException(CardErrorCode.INVALID_USER);
        }
        return card;
    }

    public Card findCard(Long cardId) {
        return cardRepository.findById(cardId)
            .orElseThrow(() -> new GlobalException(CardErrorCode.NOT_FOUND));
    }
}
