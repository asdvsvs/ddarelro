package com.b3.ddarelro.domain.card.exception;

import com.b3.ddarelro.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CardErrorCode implements ErrorCode {

    CANNOT_BE_SAME_PRIORITY(HttpStatus.FORBIDDEN, "같은 자리로는 이동 할 수 없어요!"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "카드가 존재하지 않아요!"),
    ALREADY_EXIST_WORKER(HttpStatus.BAD_REQUEST, "이미 배정된 작업자에요!"),
    NOT_EXIST_WORKER(HttpStatus.BAD_REQUEST, "해당 작업자를 찾을 수가 없어요!"),
    CANNOT_BE_SAME_COLUMN(HttpStatus.BAD_REQUEST, "카드의 컬럼id와 요청정보의 컬럼id가 달라요");

    private final HttpStatus httpStatus;
    private final String message;
}
