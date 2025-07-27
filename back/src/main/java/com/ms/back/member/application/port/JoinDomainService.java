package com.ms.back.member.application.port;

import com.ms.back.member.application.dto.JoinDTO;

public interface JoinDomainService {
    boolean joinProcess(JoinDTO joinDTO);
}
