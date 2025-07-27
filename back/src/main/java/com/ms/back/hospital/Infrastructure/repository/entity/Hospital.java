package com.ms.back.hospital.Infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name="hospital")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "hospitals")
public class Hospital {

    @Id
    @Column(name = "hospital_code")
    private String hospitalCode;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "categoryCode", nullable = false)
    private Long categoryCode;

    @Column(name = "regionCode", nullable = false)
    private Long regionCode;

    @Column(name = "districtCode", nullable = false)
    private Long districtCode;

    @Column(name = "postalCode", nullable = false)
    private Long postalCode;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "callNumber", length = 20)
    private String callNumber;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    private Hospital(String hospitalCode, String name, Long categoryCode, Long regionCode,
                     Long districtCode, Long postalCode, String address,
                     String callNumber, String latitude, String longitude) {
        this.hospitalCode = hospitalCode;
        this.name = name;
        this.categoryCode = categoryCode;
        this.regionCode = regionCode;
        this.districtCode = districtCode;
        this.postalCode = postalCode;
        this.address = address;
        this.callNumber = callNumber;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Hospital create(String hospitalCode, String name, String categoryCode, String regionCode,
                                  String districtCode, String postalCode, String address,
                                  String callNumber, String latitude, String longitude) {
        return new Hospital(
                hospitalCode,
                name,
                Long.parseLong(categoryCode),
                Long.parseLong(regionCode),
                Long.parseLong(districtCode),
                Long.parseLong(postalCode),
                address,
                callNumber,
                latitude,
                longitude
        );
    }
}
