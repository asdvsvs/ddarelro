package com.b3.ddarelro.domain.column.exception;

import com.b3.ddarelro.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ColumnErrorCode implements ErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 컬럼입니다."),
    INVALID_USER(HttpStatus.FORBIDDEN, "보드 멤버만 컬럼에 접근 가능합니다."),
    NOT_LEADER(HttpStatus.FORBIDDEN, "컬럼 생성,수정,삭제는 팀장만 가능합니다."),
    IS_DELETED(HttpStatus.BAD_REQUEST, "삭제된 컬럼입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
