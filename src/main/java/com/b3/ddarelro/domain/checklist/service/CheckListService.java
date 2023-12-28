package com.b3.ddarelro.domain.checklist.service;

import com.b3.ddarelro.domain.card.entity.Card;
import com.b3.ddarelro.domain.card.service.CardService;
import com.b3.ddarelro.domain.checklist.dto.request.CheckListCreateReq;
import com.b3.ddarelro.domain.checklist.dto.response.CheckListCompletedRes;
import com.b3.ddarelro.domain.checklist.dto.response.CheckListCreateRes;
import com.b3.ddarelro.domain.checklist.dto.response.CheckListGetDetailRes;
import com.b3.ddarelro.domain.checklist.dto.response.CheckListGetListRes;
import com.b3.ddarelro.domain.checklist.entity.CheckList;
import com.b3.ddarelro.domain.checklist.exception.CheckListErrorCode;
import com.b3.ddarelro.domain.checklist.repository.CheckListRepository;
import com.b3.ddarelro.global.exception.GlobalException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class CheckListService {

    private final CheckListRepository checkListRepository;
    private final CardService cardService;

    public CheckListCreateRes createCheckList(Long cardId, CheckListCreateReq req) {
        Card card = cardService.findCard(cardId);
        CheckList checkList = CheckList.builder().card(card).content(req.content()).build();
        checkListRepository.save(checkList);
        return CheckListCreateRes.builder().id(checkList.getId()).content(checkList.getContent())
            .build();
    }

    public CheckListGetListRes getCheckList(Long cardId) {
        Card card = cardService.findCard(cardId);
        List<CheckList> checkLists = checkListRepository.findAllByCardId(card.getId());
        List<CheckListGetDetailRes> detailRes = checkLists.stream().map(
            c -> CheckListGetDetailRes.builder().content(c.getContent()).completed(c.getCompleted())
                .build()).toList();
        return CheckListGetListRes.builder().checkList(detailRes).build();
    }

    public CheckListCompletedRes completedCheckList(Long checkListId) {
        CheckList checkList = findCheckList(checkListId);
        checkList.completeToggle();
        return CheckListCompletedRes.builder().id(checkList.getId()).content(checkList.getContent())
            .completed(checkList.getCompleted()).build();
    }

    private CheckList findCheckList(Long checkListId) {
        CheckList checkList = checkListRepository.findById(checkListId)
            .orElseThrow(() -> new GlobalException(CheckListErrorCode.NOT_FOUND));
        deleteCheck(checkList);
        return checkList;
    }

    private void deleteCheck(CheckList checkList) {
        if (checkList.getDeleted()) {
            throw new GlobalException(CheckListErrorCode.NOT_FOUND);
        }
    }
}
