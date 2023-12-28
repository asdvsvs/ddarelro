package com.b3.ddarelro.domain.user.exception;

import com.b3.ddarelro.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    EXISTS_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    EXISTS_NICKNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 닉네임입니다."),
    MISMATCH_PASSWORD(HttpStatus.BAD_REQUEST, "패스워드가 일치하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
