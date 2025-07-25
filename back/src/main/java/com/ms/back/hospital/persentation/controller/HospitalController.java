package com.ms.back.hospital.persentation.controller;

import com.ms.back.global.apiResponse.ApiResponse;
import com.ms.back.global.apiResponse.PageResponse;
import com.ms.back.hospital.Infrastructure.repository.HospitalCustomRepositoryImpl;
import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;
import com.ms.back.hospital.application.dto.HospitalInfoResponse;
import com.ms.back.hospital.application.dto.HospitalListResponse;
import com.ms.back.hospital.batch.dto.HospitalDto;
import com.ms.back.hospital.persentation.port.HospitalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Tag(name = "Hospital", description = "병원 정보 API")
@CrossOrigin(origins = "*")
@Slf4j
@Controller
@RestController
@RequestMapping("/api/hospital")
@RequiredArgsConstructor
public class HospitalController {
    private final HospitalService hospitalService;
    private final HospitalCustomRepositoryImpl hospitalCustomRepositoryImpl;

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

    //검색 기능 추가
    @GetMapping("/search")
    public ApiResponse<PageResponse<HospitalDto>> searchHospitals(
            @RequestParam(defaultValue="") String name,
            @RequestParam(defaultValue="") String address,
            @RequestParam(defaultValue="") String callNumber,
            @RequestParam(defaultValue="") String categoryCode,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        log.info("📥 병원 검색 API 호출됨: name={}, address={}, callNumber={}, categoryCode={}, page={}",
                name, address, callNumber, categoryCode, pageable.getPageNumber());

        Page<Hospital> hospitalPage = hospitalCustomRepositoryImpl.searchByKeyword(
                name, address, callNumber, categoryCode, pageable
        );
        log.info("✅ 검색 결과 개수: {}", hospitalPage.getTotalElements());

        Page<HospitalDto> dtoPage = hospitalPage.map(HospitalDto::from);
        return ApiResponse.ok(PageResponse.from(dtoPage));
    }

}
