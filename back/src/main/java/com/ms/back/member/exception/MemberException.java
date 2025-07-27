package com.ms.back.member.exception;

import com.ms.back.global.exception.ErrorCode;
import com.ms.back.global.exception.MediscanException;

public class MemberException extends MediscanException {
    public MemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
