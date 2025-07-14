package com.ms.back.hospital.controller;

import com.ms.back.global.apiResponse.ApiResponse;
import com.ms.back.global.standard.base.Empty;
import com.ms.back.hospital.util.LoadData;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final JobLauncher jobLauncher;
    private final Job loadHospitalGradeJob;

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

    @PostMapping("/batch")
    // 개발과정에서 확인을 위해 작성
    public ResponseEntity<String> runBatch() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            JobExecution jobExecution = jobLauncher.run(loadHospitalGradeJob, jobParameters);

            return ResponseEntity.ok("Batch job started with ID: " + jobExecution.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to start batch job: " + e.getMessage());
        }
    }

}
