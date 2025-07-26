package com.ms.back.member.domain.service;

import com.ms.back.global.exception.ErrorCode;
import com.ms.back.member.application.dto.LoginResponseDTO;
import com.ms.back.member.domain.model.RefreshEntity;
import com.ms.back.member.application.port.AuthDomainService;
import com.ms.back.member.domain.port.RefreshService;
import com.ms.back.global.jwt.JWTUtil;
import com.ms.back.member.exception.MemberException;
import com.ms.back.member.policy.AuthPolicy;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class AuthDomainServiceImpl implements AuthDomainService {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RefreshService refreshService; //도메인 포트 주입
    private final AuthPolicy authPolicy; // 추가됨

    @Override
    public LoginResponseDTO login(String username, String password, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            String role = authentication.getAuthorities().stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse("ROLE_USER");

            String accessToken = jwtUtil.createJwt("access", username, role, 600000L);
            String refreshToken = jwtUtil.createJwt("refresh", username, role, 86400000L);

            refreshService.deleteByUsername(username);
            addRefreshEntity(username, refreshToken, 86400000L);

            response.addCookie(createCookie("refresh", refreshToken));

            return new LoginResponseDTO(accessToken, refreshToken);
        } catch (Exception e) {
            // 예외 메시지는 숨기고, 우리가 정의한 예외 던지기
            throw new MemberException(ErrorCode.LOGIN_FAILED);
        }
    }


    @Override
    public void reissueTokens(HttpServletRequest request, HttpServletResponse response) {
        String refresh = authPolicy.validateAndExtractRefreshToken(request); // policy 사용

        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        String newAccess = jwtUtil.createJwt("access", username, role, 600000L);
        String newRefresh = jwtUtil.createJwt("refresh", username, role, 86400000L);

        refreshService.deleteByRefresh(refresh);
        addRefreshEntity(username, newRefresh, 86400000L);

        response.setHeader("access", newAccess);
        response.addCookie(createCookie("refresh", newRefresh));
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60);
        return cookie;
    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs) {
        RefreshEntity entity = new RefreshEntity();
        entity.setUsername(username);
        entity.setRefresh(refresh);
        entity.setExpiration(new Date(System.currentTimeMillis() + expiredMs).toString());
        refreshService.save(entity);
    }
}
