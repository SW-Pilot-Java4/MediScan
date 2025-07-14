package com.ms.back.hospital.dto;

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
