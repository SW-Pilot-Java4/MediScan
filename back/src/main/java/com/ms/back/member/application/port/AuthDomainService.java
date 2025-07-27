package com.ms.back.member.application.port;

import com.ms.back.member.application.dto.LoginResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthDomainService {
    LoginResponseDTO login(String username, String password, HttpServletResponse response);

    void reissueTokens(HttpServletRequest request, HttpServletResponse response);
}
