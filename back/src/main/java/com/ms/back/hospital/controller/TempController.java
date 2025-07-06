package com.ms.back.hospital.controller;

import com.ms.back.global.apiResponse.ApiResponse;
import com.ms.back.global.standard.base.Empty;
import com.ms.back.hospital.util.LoadData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Controller
@RestController
@RequestMapping("/api/temp")
@RequiredArgsConstructor
public class TempController {
    private final LoadData loadData;

    @PostMapping("/initData")
    public ApiResponse<Empty> init() {

        loadData.readSampleData();

        return ApiResponse.noContent();
    }

    @GetMapping("/test/exceptionHandler")
    public String exceptionTest() {
        loadData.exceptionTest();
        return "틀린 형태의 반환 값";
    }
    @GetMapping("/test/ok")
    public ApiResponse<String> ok() {
        return ApiResponse.ok("ok");
    }
    @GetMapping("/test/created")
    public ApiResponse<Empty> created() {
        return ApiResponse.created();
    }
    @GetMapping("/test/created_v2")
    public ApiResponse<URI> created_v2() {
        return ApiResponse.created("localhost:8080/api/temp/test/created_v2");
    }
    @GetMapping("/test/no_content")
    public ApiResponse<Empty> noContent() {
        return ApiResponse.noContent();
    }


}
