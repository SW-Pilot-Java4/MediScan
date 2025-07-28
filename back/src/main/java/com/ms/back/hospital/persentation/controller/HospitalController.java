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

    @Operation(
            summary = "근처 병원 조회",
            description = "사용자의 위도(latitude), 경도(longitude)와 반경 거리(km)를 기준으로 가까운 병원 리스트를 조회합니다. 기본 반경은 3km입니다."
    )
    @GetMapping("/nearby")
    public ApiResponse<List<HospitalListResponse>> getNearbyHospitals(
            @RequestParam String latitude,
            @RequestParam String longitude,
            @RequestParam(defaultValue = "3") double distanceKm // ← 기본값 설정!
    ) {

        return ApiResponse.ok(hospitalService.getHospitalsNearby(longitude, latitude, distanceKm));
    }

    @Operation(
            summary = "병원 검색",
            description = "병원명(name), 주소(address), 전화번호(callNumber), 분류 코드(categoryCode)를 기준으로 병원을 검색합니다. 페이징 처리가 적용됩니다."
    )
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
