package com.b3.ddarelro.domain.user.controller;

import com.b3.ddarelro.domain.user.dto.request.EmailAuthSendReq;
import com.b3.ddarelro.domain.user.dto.request.EmailCodeCheckReq;
import com.b3.ddarelro.domain.user.dto.response.EmailAuthSendRes;
import com.b3.ddarelro.domain.user.dto.response.EmailCodeCheckRes;
import com.b3.ddarelro.domain.user.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<EmailAuthSendRes> sendEmail(
        @Valid @RequestBody EmailAuthSendReq reqDto) {
        EmailAuthSendRes resDto = emailService.sendEmail(reqDto);
        return ResponseEntity.ok(resDto);
    }

    @PatchMapping
    public ResponseEntity<EmailCodeCheckRes> checkAuthCode(
        @Valid @RequestBody EmailCodeCheckReq reqDto) {

        EmailCodeCheckRes resDto = emailService.checkAuthCode(reqDto);
        return ResponseEntity.ok(resDto);
    }

}
