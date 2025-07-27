package com.ms.back.member.presentation.controller.port;

import com.ms.back.member.application.dto.JoinDTO;
import com.ms.back.member.application.dto.LoginRequestDTO;
import com.ms.back.member.application.dto.LoginResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface MemberService {

    // 회원가입
    boolean join(JoinDTO joinDTO);

    // 로그인
    LoginResponseDTO login(LoginRequestDTO request, HttpServletResponse response);

    // 토큰 재발급
    void reissueTokens(HttpServletRequest request, HttpServletResponse response);
}
