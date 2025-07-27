package com.ms.back.hospital.application.port;

import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;
import com.ms.back.hospital.application.dto.HospitalInfoResponse.BaseInfo;
import com.ms.back.hospital.application.dto.HospitalListResponse;
import com.ms.back.hospital.batch.dto.HospitalDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HospitalDomainService {
    List<Hospital> getAllHospitalData();
    BaseInfo getHospital(String hospitalCode);
    List<HospitalListResponse> getHospitalsNearby(Double latitude, Double longitude, double distanceKm);
    Page<HospitalDto> findHospitalsByKeyword (String name, String address, String callNumber, String categoryCode, Pageable pageable);
}
