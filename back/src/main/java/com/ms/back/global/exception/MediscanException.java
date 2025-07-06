package com.ms.back.global.exception;

import lombok.Getter;

@Getter
public class MediscanException extends RuntimeException{
    private final ErrorCode errorCode;

    public MediscanException(ErrorCode e) {
        super(e.getMessage());
        this.errorCode = e;
    }
}
