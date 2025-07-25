package com.ms.back.member.domain.service;

import com.ms.back.member.application.dto.LoginRequestDTO;
import com.ms.back.member.application.dto.LoginResponseDTO;
import com.ms.back.member.domain.model.RefreshEntity;
import com.ms.back.member.domain.model.UserEntity;
import com.ms.back.member.domain.port.RefreshService;
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
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class AuthDomainService {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RefreshService refreshService;  // 도메인 포트 인터페이스 주입

    public LoginResponseDTO login(String username, String password, HttpServletResponse response) {

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(authToken);

        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_USER");

        String accessToken = jwtUtil.createJwt("access", username, role, 600000L);
        String refreshToken = jwtUtil.createJwt("refresh", username, role, 86400000L);

        refreshService.deleteByUsername(username);  // 인터페이스 메서드 호출

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refreshToken);
        refreshEntity.setExpiration(new Date(System.currentTimeMillis() + 86400000L).toString());
        refreshService.save(refreshEntity);         // 인터페이스 메서드 호출

        Cookie cookie = new Cookie("refresh", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie);

        return new LoginResponseDTO(accessToken, refreshToken);
    }
}
