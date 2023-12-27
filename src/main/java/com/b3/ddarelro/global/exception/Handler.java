package com.b3.ddarelro.global.exception;

import java.util.ArrayList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Handler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ErrorResponse> handle(GlobalException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
            .status(e.getErrorCode().getHttpStatus().value())
            .build();
        errorResponse.addMessage(e.getErrorCode().getMessage());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(errorResponse);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException
        (MethodArgumentNotValidException e) {
        ArrayList<String> errors = new ArrayList<>();
        e.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));

        ErrorCode errorCode = SampleErrorCode.VALIDATION_ERROR;
        ErrorResponse errorResponse = ErrorResponse.builder()
            .status(errorCode.getHttpStatus().value())
            .messages(errors)
            .build();
        return ResponseEntity.status(errorCode.getHttpStatus())
            .body(errorResponse);

    }

}
