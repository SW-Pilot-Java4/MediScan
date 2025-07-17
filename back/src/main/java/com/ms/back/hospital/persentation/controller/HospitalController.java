package com.ms.back.hospital.persentation.controller;

import com.ms.back.global.apiResponse.ApiResponse;
import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;
import com.ms.back.hospital.application.dto.HospitalInfoResponse;
import com.ms.back.hospital.application.dto.HospitalListResponse;
import com.ms.back.hospital.persentation.port.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api/hospital")
@RequiredArgsConstructor
public class HospitalController {
    private final HospitalService hospitalService;

    @GetMapping
    public ApiResponse<List<HospitalListResponse>> getAllHospital() {
        return ApiResponse.ok(hospitalService.getAllHospitalData());
    }

    @GetMapping("/{hospitalCode}")
    public ApiResponse<HospitalInfoResponse> getHospitalDetails(@PathVariable(name = "hospitalCode") String hospitalCode) {
        return ApiResponse.ok(hospitalService.assembleHospitalInfo(hospitalCode));
    }

    //검색 기능 추가
    @GetMapping("/search")
    public ApiResponse<List<Hospital>> searchHospitals(@RequestParam String keyword){
        List<Hospital> result = hospitalService.searchHospitals(keyword);
        return ApiResponse.ok(result);
    }
}
