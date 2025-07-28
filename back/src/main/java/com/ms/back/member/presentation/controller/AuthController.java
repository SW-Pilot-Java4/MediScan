package com.ms.back.member.presentation.controller;

import com.ms.back.global.apiResponse.ApiResponse;
import com.ms.back.global.apiResponse.ApiResultType;
import com.ms.back.global.standard.base.Empty;
import com.ms.back.member.application.dto.JoinDTO;
import com.ms.back.member.application.dto.LoginRequestDTO;
import com.ms.back.member.application.dto.LoginResponseDTO;
import com.ms.back.member.presentation.controller.port.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;

@Tag(name = "Auth", description = "인증 및 회원 관련 API")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final MemberService memberService;  // 단일 서비스 주입

    @Operation(summary = "로그인", description = "사용자의 아이디와 비밀번호로 로그인을 수행하고, 엑세스 토큰을 응답 헤더에 포함시킵니다.")
    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody LoginRequestDTO request, HttpServletResponse response) {
        LoginResponseDTO loginResponse = memberService.login(request, response);

        // 헤더에 access 토큰 추가
        response.setHeader("access", loginResponse.getAccessToken());

        // 로그인 성공 → SUCCESS 타입으로 반환
        return ApiResponse.ok(loginResponse);
    }

    @Operation(summary = "회원가입", description = "회원 정보를 입력받아 신규 회원을 등록합니다.")
    @PostMapping("/join")
    public ApiResponse<String> joinProcess(@RequestBody JoinDTO joinDTO) {
        memberService.join(joinDTO);
//        if (result) {
//        } else {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 아이디입니다");
//        }
        return ApiResponse.ok("회원가입 성공");
    }

    @Operation(summary = "토큰 재발급", description = "리프레시 토큰을 이용하여 새로운 액세스 토큰과 리프레시 토큰을 발급합니다.")
    @PostMapping("/reissue")
    public ApiResponse<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        memberService.reissueTokens(request, response);
        return ApiResponse.noContent();
    }
}
