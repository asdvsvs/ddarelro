package com.b3.ddarelro.domain.checklist.service;

import com.b3.ddarelro.domain.card.entity.Card;
import com.b3.ddarelro.domain.card.service.CardService;
import com.b3.ddarelro.domain.checklist.dto.request.CheckListCreateReq;
import com.b3.ddarelro.domain.checklist.dto.response.CheckListCreateRes;
import com.b3.ddarelro.domain.checklist.dto.response.CheckListGetDetailRes;
import com.b3.ddarelro.domain.checklist.dto.response.CheckListGetListRes;
import com.b3.ddarelro.domain.checklist.entity.CheckList;
import com.b3.ddarelro.domain.checklist.repository.CheckListRepository;
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
}
