package com.ms.back.hospital.application.port;

import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;

import java.util.List;

public interface HospitalDomainService {
    List<Hospital> getAllHospitalData();
}
