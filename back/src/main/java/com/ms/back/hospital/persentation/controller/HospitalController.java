package com.ms.back.hospital.persentation.controller;

import com.ms.back.global.apiResponse.ApiResponse;
import com.ms.back.global.apiResponse.PageResponse;
import com.ms.back.hospital.application.dto.HospitalInfoResponse;
import com.ms.back.hospital.application.dto.HospitalListResponse;
import com.ms.back.hospital.batch.dto.HospitalDto;
import com.ms.back.hospital.persentation.port.HospitalService;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Hospital", description = "병원 정보 API")
@RestController
@RequestMapping("/api/hospital")
@RequiredArgsConstructor
public class HospitalController {
    private final HospitalService hospitalService;

    @Operation(summary = "전체 병원 조회", description = "전체 병원 리스트를 조회합니다.")
    @Timed(value = "controller.hospital.logic", description = "Hospital logic execution time")
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
            @RequestParam String longitude,
            @RequestParam(defaultValue = "3") double distanceKm // ← 기본값 설정!
    ) {
        return ApiResponse.ok(hospitalService.getHospitalsNearby(longitude, latitude, distanceKm));
    }

    @GetMapping("/search")
    public ApiResponse<PageResponse<HospitalDto>> searchHospitals(
            @RequestParam(defaultValue="") String name,
            @RequestParam(defaultValue="") String address,
            @RequestParam(defaultValue="") String callNumber,
            @RequestParam(defaultValue="") String categoryCode,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ){
        return ApiResponse.ok(hospitalService.getHospitalsByKeyword(name, address, callNumber, categoryCode, pageable));
    }
}
