package com.ms.back.hospital.batch.dto;

import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;

public record HospitalDto(
        String hospitalCode,
        String name,
        Long categoryCode,
        Long regionCode,
        Long districtCode,
        Long postalCode,
        String address,
        String callNumber,
        String latitude,
        String longitude
) {
    public static HospitalDto from(Hospital h) {
        return new HospitalDto(
                h.getHospitalCode(),
                h.getName(),
                h.getCategoryCode(),
                h.getRegionCode(),
                h.getDistrictCode(),
                h.getPostalCode(),
                h.getAddress(),
                h.getCallNumber(),
                h.getLatitude(),
                h.getLongitude()
        );
    }
}
