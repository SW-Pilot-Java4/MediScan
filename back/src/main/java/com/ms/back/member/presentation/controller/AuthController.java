package com.ms.back.member.presentation.controller;

import com.ms.back.member.application.dto.JoinDTO;
import com.ms.back.member.application.dto.LoginRequestDTO;
import com.ms.back.member.application.dto.LoginResponseDTO;
import com.ms.back.member.application.service.MemberServiceImpl;
import com.ms.back.member.presentation.controller.port.IMemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;

@Controller
@ResponseBody
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final IMemberService memberService;  // 단일 서비스 주입

    public AuthController(IMemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request, HttpServletResponse response) {
        LoginResponseDTO loginResponse = memberService.login(request, response);
        if (loginResponse == null) {
            return ResponseEntity.status(401).build();
        }
        response.setHeader("access", loginResponse.getAccessToken());
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/join")
    public ResponseEntity<String> joinProcess(@RequestBody JoinDTO joinDTO) {
        logger.info("🟢 /join 요청 들어옴: {}", joinDTO);
        boolean result = memberService.joinProcess(joinDTO);
        if (result) {
            logger.info("✅ 회원가입 성공: {}", joinDTO.getUsername());
            return ResponseEntity.ok("회원가입 성공");
        } else {
            logger.warn("⚠ 이미 존재하는 아이디: {}", joinDTO.getUsername());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 아이디입니다");
        }
    }

    @GetMapping("/")
    public String mainP() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        return "Main Controller: " + username + " / Role: " + role;
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        try {
            memberService.reissueTokens(request, response);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
