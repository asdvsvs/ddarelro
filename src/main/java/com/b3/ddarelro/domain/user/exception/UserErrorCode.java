package com.b3.ddarelro.domain.user.exception;

import com.b3.ddarelro.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    EXISTS_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    MISMATCH_PASSWORD(HttpStatus.BAD_REQUEST, "패스워드가 일치하지 않습니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    HAS_ADMIN_BOARD(HttpStatus.FORBIDDEN, "팀장인 보드가 있습니다. 탈퇴 전 팀장권한을 위임해주세요."),
    DELETED_USER(HttpStatus.BAD_REQUEST, "탈퇴한 사용자입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
