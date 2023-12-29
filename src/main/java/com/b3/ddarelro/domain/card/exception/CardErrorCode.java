package com.b3.ddarelro.domain.card.exception;

import com.b3.ddarelro.global.exception.*;
import lombok.*;
import org.springframework.http.*;

@Getter
@RequiredArgsConstructor
public enum CardErrorCode implements ErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "카드가 존재하지 않아요!"),
    INVALID_USER_CARD(HttpStatus.FORBIDDEN, "본인의 카드만 수정 및 삭제가 가능해요!");

    private final HttpStatus httpStatus;
    private final String message;
}
