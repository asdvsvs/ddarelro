package com.b3.ddarelro.domain.column.controller;

import com.b3.ddarelro.domain.column.dto.request.ColumnCreateReq;
import com.b3.ddarelro.domain.column.dto.request.ColumnGetReq;
import com.b3.ddarelro.domain.column.dto.response.ColumnCreateRes;
import com.b3.ddarelro.domain.column.dto.response.ColumnsGetRes;
import com.b3.ddarelro.domain.column.service.ColumnService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/columns")
@RequiredArgsConstructor
public class ColumnController {

    private final ColumnService columnService;

    @PostMapping
    public ResponseEntity<ColumnCreateRes> createColumn(@Valid @RequestBody ColumnCreateReq req,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ColumnCreateRes res = columnService.createColumn(req, userDetails.getUser().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping
    public ResponseEntity<List<ColumnsGetRes>> getColumns(@Valid @RequestBody ColumnGetReq req,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<ColumnsGetRes> res = columnService.getColumns(req, userDetails.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
