package com.ms.back.member.controller;

import com.ms.back.member.dto.LoginRequestDTO;
import com.ms.back.member.dto.LoginResponseDTO;
import com.ms.back.member.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final LoginService loginService;

    public AuthController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request, HttpServletResponse response) {

        LoginResponseDTO loginResponse = loginService.login(request, response);

        if (loginResponse == null) {
            // 로그인 실패
            return ResponseEntity.status(401).build();
        }

        // JWT 토큰이나 기타 정보는 response 헤더 또는 바디로 전달
        response.setHeader("access", loginResponse.getAccessToken());
        // refresh 토큰은 쿠키에 넣어주는 로직이 서비스 내부에 있으면 편함

        return ResponseEntity.ok(loginResponse);
    }
}
