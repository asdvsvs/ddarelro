package com.b3.ddarelro.domain.board.exception;

import com.b3.ddarelro.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BoardErrorCode implements ErrorCode {

    NOT_FOUND_BOARD(HttpStatus.NOT_FOUND,"해당보드는 존재하지 않습니다."),
    ALREADY_DELETED_BOARD(HttpStatus.BAD_REQUEST, "이미 삭제된 보드입니다."),
    UNAUTHORIZED_ACCESS_BOARD(HttpStatus.FORBIDDEN, "보드 수정 및 삭제 권한이 없습니다"),
    NOT_BOARD_MEMBER(HttpStatus.BAD_REQUEST, "해당유저는 현재 보드에 속한 멤버가 아닙니다."),
    FORBIDDEN_INVITE_OWN(HttpStatus.FORBIDDEN, "자기 자신을 초대시킬 수 없습니다."),
    ALREADY_BOARD_MEMBER(HttpStatus.BAD_REQUEST, "해당유저는 이미 초대된 멤버입니다."),
    REQUIRED_NEW_BOARD_ADMIN(HttpStatus.BAD_REQUEST, "팀장권한을 위임할 사용하자가 필요합니다.");



    private final HttpStatus httpStatus;
    private final String message;
}