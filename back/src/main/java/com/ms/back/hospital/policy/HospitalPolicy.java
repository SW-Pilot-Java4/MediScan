package com.ms.back.hospital.policy;

import com.ms.back.global.exception.MediscanException;
import org.springframework.stereotype.Component;

import static com.ms.back.global.exception.ErrorCode.BAD_REQUEST;

@Component
public class HospitalPolicy {
    public void exceptionTest(boolean b) {
        if(b) throw new MediscanException(BAD_REQUEST);
    }
}
