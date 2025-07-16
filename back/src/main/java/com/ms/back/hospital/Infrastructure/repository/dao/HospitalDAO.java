package com.ms.back.hospital.Infrastructure.repository.dao;

import com.ms.back.hospital.batch.dto.HospitalRegister;
import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;

public record HospitalDAO(
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

    public static HospitalDAO from(HospitalRegister dto) {
        return new HospitalDAO(
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

    public static HospitalDAO from(Hospital entity) {
        return new HospitalDAO(
                entity.getHospitalCode(),
                entity.getName(),
                String.valueOf(entity.getCategoryCode()),
                String.valueOf(entity.getRegionCode()),
                String.valueOf(entity.getDistrictCode()),
                String.valueOf(entity.getPostalCode()),
                entity.getAddress(),
                entity.getCallNumber(),
                entity.getLatitude(),
                entity.getLongitude()
        );
    }
}
