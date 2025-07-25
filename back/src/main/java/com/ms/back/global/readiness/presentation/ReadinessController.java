package com.ms.back.global.readiness.presentation;

import com.ms.back.global.readiness.application.ReadinessService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReadinessController {
    private final ReadinessService readinessService;

    @GetMapping("/ready")
    @Hidden
    public ResponseEntity<String> isReady() {
        boolean ready = readinessService.checkApplicationReadiness();

        if (ready) {
            return ResponseEntity.ok("Application is ready");
        } else {
            // 준비 되지 않았다면, 503 상태 코드와 메시지 반환
            return ResponseEntity.status(503).body("Application is not ready");
        }
    }

    @GetMapping("/notready")
    @Hidden
    public ResponseEntity<String> isNotReady() {
        boolean ready = readinessService.mockApplicationReadinessFailCase();

        if (ready) {
            return ResponseEntity.ok("Application is ready");
        } else {
            // 준비 되지 않았다면, 503 상태 코드와 메시지 반환
            return ResponseEntity.status(503).body("Application is not ready");
        }
    }
}
