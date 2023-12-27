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
    public ResponseEntity<CardCreateRes> createCard(@RequestBody CardCreateReq reqDto) {

        CardCreateRes resDto = cardService.createCard(reqDto);
        return ResponseEntity.status(201).body(resDto);
    }

    //TODO 카드 목록 조회
    @GetMapping
    public ResponseEntity<List<CardListRes>> getCardList() {
        List<CardListRes> resListDto = cardService.getCardList();
        return ResponseEntity.ok().body(resListDto);
    }

    //TODO 카드 단일 조회

    //TODO 카드 작업자 할당

    //TODO 카드 수정

    //TODO 카드 삭제

    //TODO 카드 콜롬간 이동
}
