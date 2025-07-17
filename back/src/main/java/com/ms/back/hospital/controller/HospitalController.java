package com.ms.back.hospital.controller;

import com.ms.back.global.apiResponse.ApiResponse;
import com.ms.back.hospital.entity.Hospital;
import com.ms.back.hospital.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api/hospital")
@RequiredArgsConstructor
public class HospitalController {
    private final HospitalService hospitalService;

    @GetMapping
    public ApiResponse<List<Hospital>> getAllHospital() {
        return ApiResponse.ok(hospitalService.getAllData());
    }

    //검색 기능 추가
    @GetMapping("/search")
    public ApiResponse<List<Hospital>> searchHospitals(@RequestParam String keyword){
        List<Hospital> result = hospitalService.searchHospitals(keyword);
        return ApiResponse.ok(result);
    }
}
