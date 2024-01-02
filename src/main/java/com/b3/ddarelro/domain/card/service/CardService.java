package com.b3.ddarelro.domain.card.service;

import com.b3.ddarelro.domain.card.dto.request.*;
import com.b3.ddarelro.domain.card.dto.response.*;
import com.b3.ddarelro.domain.card.entity.*;
import com.b3.ddarelro.domain.card.exception.*;
import com.b3.ddarelro.domain.card.repository.*;
import com.b3.ddarelro.domain.checklist.service.*;
import com.b3.ddarelro.domain.column.entity.*;
import com.b3.ddarelro.domain.column.service.*;
import com.b3.ddarelro.domain.comment.service.*;
import com.b3.ddarelro.domain.file.service.*;
import com.b3.ddarelro.domain.user.entity.*;
import com.b3.ddarelro.domain.user.service.*;
import com.b3.ddarelro.domain.worker.*;
import com.b3.ddarelro.domain.worker.entity.*;
import com.b3.ddarelro.domain.worker.repository.*;
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
    private final FileDeleteRestoreService fileDeleteRestoreService;
    private final CheckListDeleteRestoreService checkListDeleteRestoreService;
    private final WorkerRepository workerRepository;

    @Transactional
    public CardCreateRes createCard(CardCreateReq req, User user) {
        userService.findUser(user.getId());
        Column column = columnService.findColumn(req.columnId());

        Long priority = cardRepository.countByColumnId(req.columnId()) + 1;

        Card newCard = Card.builder()
            .name(req.name())
            .column(column)
            .description(req.description())
            .color(req.color())
            .priority(priority)
            .build();
        cardRepository.save(newCard);
        return CardCreateRes.formingWith(newCard, priority);
    }

    public List<CardListRes> getCardList(CardListReq req, User user) {
        userService.findUser(user.getId());
        Column column = columnService.findColumn(req.columnId());

        List<Card> cardList = cardRepository.findAllByColumnIdAndNotDeleted(column.getId());

        return cardList.stream().map(CardListRes::formWith)
            .collect(Collectors.toList());
    }

    @Transactional
    public CardRes getCard(Long cardId, CardReq req, User user) {
        userService.findUser(user.getId());
        columnService.findColumn(req.columnId());
        Card card = findCard(cardId);

        List<WorkersRes> workersRes = getWorkers(card);

        return CardRes.formWith(card, workersRes);
    }

    private static List<WorkersRes> getWorkers(Card card) {
        List<WorkersRes> workersRes = new ArrayList<>();
        List<Worker> assignedWorkers = card.getWorkers();
        for (Worker worker : assignedWorkers) {
            workersRes.add(WorkersRes.formWith(worker));
        }
        return workersRes;
    }

    @Transactional
    public CardModifyRes modifyCard(Long cardId, CardModifyReq req, User user) {
        userService.findUser(user.getId());
        columnService.findColumn(req.columnId());

        Card card = findCard(cardId);

        card.modifyCard(req);
        return CardModifyRes.formWith(card);
    }

    @Transactional
    public CardDueDateRes setDueDateCard(Long cardId, CardDueDateReq req, User user) {
        userService.findUser(user.getId());
        columnService.findColumn(req.columnId());

        Card card = findCard(cardId);

        LocalDate dueDate = getDueDate(req);
        card.setDueDate(dueDate);
        return CardDueDateRes.formWith(card, dueDate);

    }

    @Transactional
    public CardMoveRes moveCard(Long cardId, CardMoveReq req, User user) {
        userService.findUser(user.getId());
        Column column = columnService.findColumn(req.columnId());

        Card card = findCard(cardId);
        if (req.anotherColumnId() != null) {
            //현재컬럼내 카드들 마지막 순위로
            Long spotInSameColumn = cardRepository.countByColumnId(column.getId());
            card.moveSpot(spotInSameColumn);
            //나머지 카드들 순위순 정리
            orderByPriorityOtherCards(card, spotInSameColumn);
            //다른 컬럼으로 카드 이동
            Column anotherColumn = columnService.findColumn(req.anotherColumnId());
            card.changeColumn(anotherColumn);
            //이동한 다른 컬럼내 위치 이동
            movePosition(req.spot(), card, anotherColumn.getId());

            return CardMoveRes.formWith(card);
        }
        movePosition(req.spot(), card, column.getId());

        return CardMoveRes.formWith(card);
    }

    private void movePosition(Long spot, Card card, Long columnId) {
        spot = defineSpot(spot, columnId);
        if (Objects.equals(card.getPriority(), spot)) {
            throw new GlobalException(CardErrorCode.CANNOT_BE_SAME_PRIORITY);
        }
        orderByPriorityOtherCards(card, spot);
        card.moveSpot(spot);
    }

    private void orderByPriorityOtherCards(Card card, Long spotInSameColumn) {
        Long moveDirection = card.getPriority() > spotInSameColumn ? 1L : -1L;
        Long start = moveDirection == -1L ? card.getPriority() : spotInSameColumn;
        Long end = moveDirection == -1L ? spotInSameColumn : card.getPriority();
        cardRepository.moveAnotherCards(start, end, moveDirection);
    }

    private Long defineSpot(Long spot, Long columnId) {
        Long cardCnt = cardRepository.countByColumnId(columnId);
        if (spot < 1) {
            spot = 1L;
        } else if (spot > cardCnt) {
            spot = cardCnt;
        }
        return spot;
    }

    @Transactional
    public CardDeleteRes deleteCard(Long cardId, CardDeleteReq req, User user) {
        userService.findUser(user.getId());
        Column column = columnService.findColumn(req.columnId());

        List<Long> cardIdList = findCardIdsByColumn(cardId, column);
        commentDeleteRestoreService.deleteAllComment(cardIdList);
        fileDeleteRestoreService.deleteAllFiles(cardIdList);
        checkListDeleteRestoreService.deleteAllComment(cardIdList);
        return CardDeleteRes.builder().msg("카드가 삭제 됬어요!").build();
    }

    @Transactional
    public CardRestoreRes restoreCard(Long cardId, CardRestoreReq req, User user) {
        userService.findUser(user.getId());
        Column column = columnService.findColumn(req.columnId());

        List<Long> cardIdList = findCardIdsByColumn(cardId, column);
        commentDeleteRestoreService.restoreAllComment(cardIdList);
        fileDeleteRestoreService.restoreAllFiles(cardIdList);
        checkListDeleteRestoreService.restoreAllComment(cardIdList);
        return CardRestoreRes.builder().msg("카드가 복구 됬어요!").build();
    }

    //TODO 작업자 할당
    @Transactional
    public CardWorkersRes setWorkers(CardWorkersReq req, Long userId) {
        User user = userService.findUser(userId);
        Card card = findCard(req.cardId());

        Optional<Worker> checkWorkerName = workerRepository.findByWorkerNameAndCard(
            req.workerName(), card);

        //배정되지 않은 작업자면 username으로 Worker생성 후 저장
        if (checkWorkerName.isEmpty()) {
            setNewWorker(req, user, card);
        }
        //이미 배정되어 있는 작업자면 삭제
        checkWorkerName.ifPresent(workerRepository::delete);

        return CardWorkersRes.builder().msg("작업배정이 업데이트 되었습니다!").build();
    }

    private static LocalDate getDueDate(CardDueDateReq req) {
        int year = req.year();
        int month = req.month();
        int day = req.day();
        LocalDate dueDate;

        dueDate = LocalDate.of(year, month, day);
        return dueDate;
    }

    private void setNewWorker(CardWorkersReq req, User user, Card card) {
        Worker newWorker = Worker.builder()
            .workerName(req.workerName())
            .user(user)
            .card(card)
            .isAssigned(Assignment.ASSIGNED)
            .build();
        //등록할 작업자가 사용자로 등록되어 있는지 검증
        if (!req.workerName()
            .equals(userService.findUser(newWorker.getUser().getId()).getUsername())) {
            throw new GlobalException(CardErrorCode.NOT_EXIST_WORKER);
        }
        workerRepository.save(newWorker);
    }

    private List<Long> findCardIdsByColumn(Long cardId, Column column) {
        Card card = findCard(cardId);
        card.deleteRestoreCard();
        List<Card> cardList = cardRepository.findAllByColumnIdAndNotDeleted(column.getId());
        return cardList.stream().map(Card::getId).toList();
    }

    public Card findCard(Long cardId) {
        return cardRepository.findById(cardId)
            .orElseThrow(() -> new GlobalException(CardErrorCode.NOT_FOUND));
    }
}
