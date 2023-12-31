package com.b3.ddarelro.domain.comment.exception;

import com.b3.ddarelro.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode implements ErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND,"존재하지 않는 댓글입니다."),
    INVALID_USER(HttpStatus.FORBIDDEN, "본인의 댓글만 수정 및 삭제가 가능합니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
