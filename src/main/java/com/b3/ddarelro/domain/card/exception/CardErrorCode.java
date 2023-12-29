package com.b3.ddarelro.domain.card.exception;

import com.b3.ddarelro.global.exception.*;
import lombok.*;
import org.springframework.http.*;

@Getter
@RequiredArgsConstructor
public enum CardErrorCode implements ErrorCode {

    CANNOT_BE_SAME_PRIORITY(HttpStatus.FORBIDDEN, "같은 자리로는 이동 할 수 없어요!"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "카드가 존재하지 않아요!");

    private final HttpStatus httpStatus;
    private final String message;
}
