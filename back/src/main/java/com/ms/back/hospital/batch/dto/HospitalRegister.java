package com.ms.back.hospital.batch.dto;

public record HospitalRegister(
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

}
