package com.ms.back.hospital.exception;

import com.ms.back.global.exception.ErrorCode;
import com.ms.back.global.exception.MediscanException;

import static com.ms.back.global.exception.ErrorCode.HOSPITAL_NOT_EXIST;

public class HospitalException extends MediscanException {
    public HospitalException(ErrorCode e) {
        super(e);
    }

    public static class HospitalNotExist extends HospitalException {
        public HospitalNotExist() {
            super(HOSPITAL_NOT_EXIST);
        }
    }
}
