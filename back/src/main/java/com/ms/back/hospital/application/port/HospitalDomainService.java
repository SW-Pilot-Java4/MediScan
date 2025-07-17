package com.ms.back.hospital.application.port;

import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;
import com.ms.back.hospital.application.dto.HospitalInfoResponse.BaseInfo;

import java.util.List;

public interface HospitalDomainService {
    List<Hospital> getAllHospitalData();

    BaseInfo getHospital(String hospitalCode);
}
