package com.b3.ddarelro.domain.user.exception;

import com.b3.ddarelro.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum EmailErrorCode implements ErrorCode {

    NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND, "존재하지 않는 이메일입니다."),
    EXPIRED_CODE(HttpStatus.BAD_REQUEST, "코드가 일치하지 않습니다");


    private final HttpStatus httpStatus;
    private final String message;
}
