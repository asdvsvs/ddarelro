package com.b3.ddarelro.domain.card.controller;

import com.b3.ddarelro.domain.card.dto.request.*;
import com.b3.ddarelro.domain.card.dto.response.*;
import com.b3.ddarelro.domain.card.service.*;
import com.b3.ddarelro.global.security.*;
import java.util.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.core.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<CardCreateRes> createCard(@RequestBody CardCreateReq req,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        CardCreateRes res = cardService.createCard(req, userDetails.getUser());
        return ResponseEntity.status(201).body(res);
    }

    @GetMapping
    public ResponseEntity<List<CardListRes>> getCardList(@RequestBody CardListReq req,
        @AuthenticationPrincipal
        UserDetailsImpl userDetails) {
        List<CardListRes> resList = cardService.getCardList(req, userDetails.getUser());
        return ResponseEntity.ok().body(resList);
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<CardRes> getCard(@PathVariable Long cardId, @RequestBody CardReq req,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CardRes res = cardService.getCard(cardId, req, userDetails.getUser());
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
        @RequestBody CardModifyReq req, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CardModifyRes res = cardService.modifyCard(cardId, req, userDetails.getUser());
        return ResponseEntity.ok().body(res);
    }

    @PatchMapping("/{cardId}/dueDate")
    public ResponseEntity<CardDueDateRes> setDueDateCard(@PathVariable Long cardId,
        @RequestBody CardDueDateReq req, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CardDueDateRes res = cardService.setDueDateCard(cardId, req, userDetails.getUser());
        return ResponseEntity.ok().body(res);
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<CardDeleteRes> deleteCard(@PathVariable Long cardId,
        @RequestBody CardDeleteReq req, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CardDeleteRes res = cardService.deleteCard(cardId, req,
            userDetails.getUser());
        return ResponseEntity.ok().body(res);
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<CardRestoreRes> restore(@PathVariable Long cardId,
        @RequestBody CardRestoreReq req, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CardRestoreRes res = cardService.restoreCard(cardId, req, userDetails.getUser());
        return ResponseEntity.ok().body(res);
    }

    //TODO 카드 콜롬간 이동
//    @PutMapping

}
