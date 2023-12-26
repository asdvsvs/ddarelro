package com.b3.ddarelro.global.exception;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException{

    SampleErrorCode sampleErrorCode;

    public GlobalException(SampleErrorCode sampleErrorCode) {
        this.sampleErrorCode = sampleErrorCode;
    }
}
