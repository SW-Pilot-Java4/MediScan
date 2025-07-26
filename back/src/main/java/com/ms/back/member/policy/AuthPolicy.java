package com.ms.back.member.policy;

import com.ms.back.global.jwt.JWTUtil;
import com.ms.back.member.domain.port.RefreshService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthPolicy {

    private final JWTUtil jwtUtil;
    private final RefreshService refreshService;

    public String validateAndExtractRefreshToken(HttpServletRequest request) {
        String refresh = extractRefreshToken(request);

        if (refresh == null) {
            throw new RuntimeException("refresh token null");
        }

        try {
            jwtUtil.isExpired(refresh);
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            throw new RuntimeException("refresh token expired");
        }

        if (!"refresh".equals(jwtUtil.getCategory(refresh))) {
            throw new RuntimeException("invalid refresh token");
        }

        if (!refreshService.existsByRefresh(refresh)) {
            throw new RuntimeException("invalid refresh token");
        }

        return refresh;
    }

    private String extractRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;

        for (Cookie cookie : cookies) {
            if ("refresh".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
