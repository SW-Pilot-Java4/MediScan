package com.ms.back.hospital.persentation.controller;

import com.ms.back.global.apiResponse.ApiResponse;
import com.ms.back.hospital.application.dto.HospitalInfoResponse;
import com.ms.back.hospital.application.dto.HospitalListResponse;
import com.ms.back.hospital.persentation.port.HospitalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Tag(name = "Hospital", description = "병원 정보 API")
@RestController
@RequestMapping("/api/hospital")
@RequiredArgsConstructor
public class HospitalController {
    private final HospitalService hospitalService;

    @Operation(summary = "전체 병원 조회", description = "전체 병원 리스트를 조회합니다.")
    @GetMapping
    public ApiResponse<List<HospitalListResponse>> getAllHospital() {
        return ApiResponse.ok(hospitalService.getAllHospitalData());
    }

    @Operation(summary = "병원 상세 조회", description = "병원 코드에 따라 상세 정보를 조회합니다.")
    @GetMapping("/{hospitalCode}")
    public ApiResponse<HospitalInfoResponse> getHospitalDetails(@PathVariable(name = "hospitalCode") String hospitalCode) {
        return ApiResponse.ok(hospitalService.assembleHospitalInfo(hospitalCode));
    }


    @GetMapping("/nearby")
    public ApiResponse<List<HospitalListResponse>> getNearbyHospitals(
            @RequestParam String latitude,
            @RequestParam String longitude
    ) {
//        double lat = Double.parseDouble(latitude);
//        double lng = Double.parseDouble(longitude);

//        System.out.println("📍 프론트에서 받은 위도: " + lat);
//        System.out.println("📍 프론트에서 받은 경도: " + lng);

        List<HospitalListResponse> nearbyHospitals = hospitalService.getHospitalsNearby(latitude, longitude, 3.0);

        return ApiResponse.ok(nearbyHospitals);
    }
}
