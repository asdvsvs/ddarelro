package com.b3.ddarelro.domain.checklist.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.b3.ddarelro.domain.checklist.dto.request.CheckListCreateReq;
import com.b3.ddarelro.domain.checklist.dto.response.CheckListCreateRes;
import com.b3.ddarelro.domain.checklist.service.CheckListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CheckListController {

    private final CheckListService checkListService;

    @PostMapping("/{cardId}/checkList")
    public ResponseEntity<CheckListCreateRes> createCheckList(@PathVariable Long cardId,
        @RequestBody @Valid CheckListCreateReq req) {

        CheckListCreateRes res = checkListService.createCheckList(cardId, req);
        return ResponseEntity.status(CREATED).body(res);
    }
}