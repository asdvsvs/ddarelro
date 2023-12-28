package com.b3.ddarelro.domain.file.exception;

import com.b3.ddarelro.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FileErrorCod implements ErrorCode {


    NOT_FOUND_FILE(HttpStatus.NOT_FOUND, "존재하지않은 파일입니다.");


    private final HttpStatus httpStatus;
    private final String message;


}
