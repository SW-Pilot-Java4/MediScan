package com.ms.back.member.application.service;

import com.ms.back.member.application.dto.JoinDTO;
import com.ms.back.member.application.dto.LoginRequestDTO;
import com.ms.back.member.application.dto.LoginResponseDTO;
import com.ms.back.member.application.port.AuthDomainService;
import com.ms.back.member.application.port.JoinDomainService;
import com.ms.back.member.presentation.controller.port.IMemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberServiceImpl implements IMemberService {

    private final AuthDomainService authDomainService;
    private final JoinDomainService joinDomainService;

    @Override
    public LoginResponseDTO login(LoginRequestDTO request, HttpServletResponse response) {
        try {
            return authDomainService.login(request.getUsername(), request.getPassword(), response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean joinProcess(JoinDTO joinDTO) {
        return joinDomainService.joinProcess(joinDTO);
    }

    @Override
    public void reissueTokens(HttpServletRequest request, HttpServletResponse response) {
        authDomainService.reissueTokens(request, response);
    }
}
