package com.b3.ddarelro.domain.column.exception;

import com.b3.ddarelro.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ColumnErrorCode implements ErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 컬럼입니다."),
    IS_DELETED(HttpStatus.BAD_REQUEST, "삭제된 컬럼입니다."),
    CANNOT_BE_SAME_PRIORITY(HttpStatus.BAD_REQUEST, "동일한 위치로는 옮길 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
