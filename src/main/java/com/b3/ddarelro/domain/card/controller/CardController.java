package com.b3.ddarelro.domain.card.controller;

import com.b3.ddarelro.domain.card.dto.request.*;
import com.b3.ddarelro.domain.card.dto.response.*;
import com.b3.ddarelro.domain.card.service.*;
import java.util.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<CardCreateRes> createCard(@RequestBody CardCreateReq req) {

        CardCreateRes res = cardService.createCard(req);
        return ResponseEntity.status(201).body(res);
    }

    @GetMapping
    public ResponseEntity<List<CardListRes>> getCardList() {
        List<CardListRes> resList = cardService.getCardList();
        return ResponseEntity.ok().body(resList);
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<CardRes> getCard(@PathVariable Long cardId) {
        CardRes res = cardService.getCard(cardId);
        return ResponseEntity.ok().body(res);
    }

    //TODO 카드 작업자 할당
//    @PutMapping("/{cardId}")
//    public ResponseEntity<CardWorkersRes> addWorkers(@PathVariable Long cardId,
//        @RequestBody CardWorkersReq req, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        CardWorkersRes res = cardService.addWorkers(cardId, req, userDetails.getUser());
//        return ResponseEntity.ok().body(res);
//    }

    @PatchMapping("/{cardId}")
    public ResponseEntity<CardModifyRes> modifyCard(@PathVariable Long cardId,
        @RequestBody CardModifyReq req) {
        CardModifyRes res = cardService.modifyCard(cardId, req);
        return ResponseEntity.ok().body(res);
    }

    //TODO 마감일 설정
    @PatchMapping("/{cardId}/dueDate")
    public ResponseEntity<CardDueDateRes> setDueDateCard(@PathVariable Long cardId,
        @RequestBody CardDueDateReq req) {
        CardDueDateRes res = cardService.setDueDateCard(cardId, req);
        return ResponseEntity.ok().body(res);
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<CardDeleteRes> deleteCard(@PathVariable Long cardId,
        @RequestBody CardDeleteReq req) {
        CardDeleteRes resDto = cardService.deleteCard(cardId, req);
        return ResponseEntity.ok().body(resDto);
    }

    //TODO 카드 콜롬간 이동
//    @PutMapping

}
