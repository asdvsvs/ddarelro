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
        return ResponseEntity.status(201).body(cardService.createCard(req, userDetails.getUser()));
    }

    @GetMapping
    public ResponseEntity<List<CardListRes>> getCardList(@RequestBody CardListReq req,
        @AuthenticationPrincipal
        UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(cardService.getCardList(req, userDetails.getUser()));
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<CardRes> getCard(@PathVariable Long cardId, @RequestBody CardReq req,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(cardService.getCard(cardId, req, userDetails.getUser()));
    }

    @PatchMapping("/{cardId}")
    public ResponseEntity<CardModifyRes> modifyCard(@PathVariable Long cardId,
        @RequestBody CardModifyReq req, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(cardService.modifyCard(cardId, req, userDetails.getUser()));
    }

    @PatchMapping("/{cardId}/dueDate")
    public ResponseEntity<CardDueDateRes> setDueDateCard(@PathVariable Long cardId,
        @RequestBody CardDueDateReq req, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok()
            .body(cardService.setDueDateCard(cardId, req, userDetails.getUser()));
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<CardDeleteRes> deleteCard(@PathVariable Long cardId,
        @RequestBody CardDeleteReq req, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(cardService.deleteCard(cardId, req,
            userDetails.getUser()));
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<CardRestoreRes> restoreCard(@PathVariable Long cardId,
        @RequestBody CardRestoreReq req, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok()
            .body(cardService.restoreCard(cardId, req, userDetails.getUser()));
    }

    @PutMapping("/{cardId}/move")
    public ResponseEntity<CardMoveRes> moveCard(@PathVariable Long cardId,
        @RequestBody CardMoveReq req, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(cardService.moveCard(cardId, req, userDetails.getUser()));
    }

    //TODO 카드 작업자 할당
//    @PutMapping("/{cardId}")
//    public ResponseEntity<List<CardWorkersRes>> addWorkers(@PathVariable Long cardId,
//        @RequestBody CardWorkersReq req, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return ResponseEntity.ok().body(cardService.addWorkers(cardId, req, userDetails.getUser()));
//    }
}
