package com.ms.back.member.application.service;

import com.ms.back.member.application.dto.LoginRequestDTO;
import com.ms.back.member.application.dto.LoginResponseDTO;
import com.ms.back.member.domain.model.RefreshEntity;
import com.ms.back.member.domain.service.AuthDomainService;
import com.ms.back.member.infrastructure.repository.RefreshRepository;
import com.ms.back.member.infrastructure.repository.UserRepository;
import com.ms.back.global.jwt.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthDomainService authDomainService;

    public LoginResponseDTO login(LoginRequestDTO request, HttpServletResponse response) {
        try {
            return authDomainService.login(request.getUsername(), request.getPassword(), response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
