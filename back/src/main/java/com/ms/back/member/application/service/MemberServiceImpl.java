package com.ms.back.member.application.service;

import com.ms.back.member.application.dto.JoinDTO;
import com.ms.back.member.application.dto.LoginRequestDTO;
import com.ms.back.member.application.dto.LoginResponseDTO;
import com.ms.back.member.application.port.AuthDomainService;
import com.ms.back.member.application.port.JoinDomainService;
import com.ms.back.member.presentation.controller.port.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final AuthDomainService authDomainService;
    private final JoinDomainService joinDomainService;

    @Override
    public LoginResponseDTO login(LoginRequestDTO request, HttpServletResponse response) {
        LoginResponseDTO loginResponse;

        try {
            loginResponse = authDomainService.login(request.getUsername(), request.getPassword(), response);
        } catch (Exception e) {
            loginResponse = null;
            // TODO: 예외 발생
        }
        return loginResponse;

//        LoginResponseDTO loginResponse = memberService.login(request, response);
//        if (loginResponse == null) {
//            return ResponseEntity.status(401).build();
//        }
//        response.setHeader("access", loginResponse.getAccessToken());
    }

    @Override
    @Transactional
    public boolean join(JoinDTO joinDTO) {
        return joinDomainService.join(joinDTO);
    }

    @Override
    public void reissueTokens(HttpServletRequest request, HttpServletResponse response) {
        authDomainService.reissueTokens(request, response);
    }
}
