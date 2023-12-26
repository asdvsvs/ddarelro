package com.b3.ddarelro.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SampleErrorCode implements ErrorCode{

    NOT_FOUND(HttpStatus.NOT_FOUND,"찾을 수 없음"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Validation에러입니다. 입력 조건을 확인해주세요");


    private final HttpStatus httpStatus;
    private final String message;
}
