package com.ms.back.hospital.repository.dao;

import com.ms.back.hospital.dto.HospitalRegister;
import com.ms.back.hospital.entity.Hospital;

public record HospitalRegisterDAO(
        String hospitalCode,
        String name,
        String categoryCode,
        String regionCode,
        String districtCode,
        String postalCode,
        String address,
        String callNumber,
        String latitude,
        String longitude
) {
    public Hospital to() {
        return Hospital.create(
                hospitalCode,
                name,
                categoryCode,
                regionCode,
                districtCode,
                postalCode,
                address,
                callNumber,
                latitude,
                longitude
        );
    }

    public static HospitalRegisterDAO from(HospitalRegister dto) {
        return new HospitalRegisterDAO(
                dto.hospitalCode(),
                dto.name(),
                dto.categoryCode(),
                dto.regionCode(),
                dto.districtCode(),
                dto.postalCode(),
                dto.address(),
                dto.callNumber(),
                dto.latitude(),
                dto.longitude()
        );
    }
}
