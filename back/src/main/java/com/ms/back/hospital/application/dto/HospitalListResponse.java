package com.ms.back.hospital.application.dto;

import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;

public record HospitalListResponse(
        String hospital_code,
        String name,
        String address,
        String latitude,
        String longitude
) {
    public static HospitalListResponse from(Hospital entity) {
        return new HospitalListResponse(
                entity.getHospitalCode(),
                entity.getName(),
                entity.getAddress(),
                entity.getLatitude(),
                entity.getLongitude()
        );
    }

    // record 필드가 이미 getter 역할을 하지만, 필요시 아래 메서드 추가 가능
    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
