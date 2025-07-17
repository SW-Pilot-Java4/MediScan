package com.ms.back.hospital.application.dto;

import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;

public record HospitalListResponse(
        String hospital_code,
        String name,
        String address
) {
    public static HospitalListResponse from(Hospital entity) {
        return new HospitalListResponse(
          entity.getHospitalCode(),
          entity.getName(),
          entity.getAddress()
        );
    }
}
