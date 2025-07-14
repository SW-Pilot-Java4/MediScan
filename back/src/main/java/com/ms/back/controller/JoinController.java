package com.ms.back.controller;

import com.ms.back.dto.JoinDTO;
import com.ms.back.service.JoinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; // 로그 라이브러리 추가
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class JoinController {

    private static final Logger logger = LoggerFactory.getLogger(JoinController.class); // ✅ 로그 선언

    private final JoinService joinService;

    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    @PostMapping("/join")
    public ResponseEntity<String> joinProcess(@RequestBody JoinDTO joinDTO) {
        logger.info("🟢 /join 요청 들어옴: {}", joinDTO); // ✅ 로그 찍기 (DTO 내용도 확인)

        boolean result = joinService.joinProcess(joinDTO);
        if (result) {
            logger.info("✅ 회원가입 성공: {}", joinDTO.getUsername());
            return ResponseEntity.ok("회원가입 성공");
        } else {
            logger.warn("⚠ 이미 존재하는 아이디: {}", joinDTO.getUsername());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 아이디입니다");
        }
    }
}
