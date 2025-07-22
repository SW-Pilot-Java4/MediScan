package com.ms.back.hospital.persentation.controller;

import com.ms.back.global.apiResponse.ApiResponse;
import com.ms.back.hospital.application.dto.HospitalInfoResponse;
import com.ms.back.hospital.application.dto.HospitalListResponse;
import com.ms.back.hospital.persentation.port.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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


    @PostMapping("/nearby")
    public ApiResponse<List<HospitalListResponse>> getNearbyHospitals(
            @RequestBody Map<String, Object> location
    ) {
        double lat = Double.parseDouble(location.get("latitude").toString());
        double lng = Double.parseDouble(location.get("longitude").toString());

        System.out.println("📍 프론트에서 받은 위도: " + lat);
        System.out.println("📍 프론트에서 받은 경도: " + lng);

        List<HospitalListResponse> nearbyHospitals = hospitalService.getHospitalsNearby(lat, lng, 3.0);

        return ApiResponse.ok(nearbyHospitals);
    }
}
