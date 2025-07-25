package com.ms.back.member.persentation.controller;

import com.ms.back.member.application.dto.LoginRequestDTO;
import com.ms.back.member.application.dto.LoginResponseDTO;
import com.ms.back.member.application.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final LoginService loginService;

    public AuthController(LoginService loginService) {
        this.loginService = loginService;
    }

    // 로그인 페이지 보여주기
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // 뷰 이름 (login.html 등)
    }

    // 로그인 API
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request, HttpServletResponse response) {
        LoginResponseDTO loginResponse = loginService.login(request, response);

        if (loginResponse == null) {
            return ResponseEntity.status(401).build();
        }

        response.setHeader("access", loginResponse.getAccessToken());
        // refresh token 쿠키 세팅 등은 loginService 내부에서 처리 가능

        return ResponseEntity.ok(loginResponse);
    }
}
