package com.ms.back.member.service;

import com.ms.back.member.dto.LoginRequestDTO;
import com.ms.back.member.dto.LoginResponseDTO;
import com.ms.back.member.entity.RefreshEntity;
import com.ms.back.member.entity.UserEntity;
import com.ms.back.member.repository.RefreshRepository;
import com.ms.back.member.repository.UserRepository;
import com.ms.back.member.jwt.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;

@Service
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public LoginService(AuthenticationManager authenticationManager,
                        JWTUtil jwtUtil,
                        RefreshRepository refreshRepository,
                        UserRepository userRepository,
                        BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public LoginResponseDTO login(LoginRequestDTO request, HttpServletResponse response) {
        System.out.println("로그인 시도: " + request.getUsername());
        System.out.println("비밀번호: " + request.getPassword());
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            String username = authentication.getName();
            Iterator<? extends GrantedAuthority> iterator = authentication.getAuthorities().iterator();
            String role = iterator.hasNext() ? iterator.next().getAuthority() : "ROLE_USER";

            String accessToken = jwtUtil.createJwt("access", username, role, 600000L);
            String refreshToken = jwtUtil.createJwt("refresh", username, role, 86400000L);

            // 기존에 저장된 리프레시 토큰을 username으로 삭제
            refreshRepository.deleteByUsername(username);

            RefreshEntity refreshEntity = new RefreshEntity();
            refreshEntity.setUsername(username);
            refreshEntity.setRefresh(refreshToken);
            refreshEntity.setExpiration(new Date(System.currentTimeMillis() + 86400000L).toString());
            refreshRepository.save(refreshEntity);

            Cookie cookie = new Cookie("refresh", refreshToken);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60);
            response.addCookie(cookie);

            return new LoginResponseDTO(accessToken, role);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

