package com.ms.back.hospital.domain.port;

import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;

import java.util.List;
import java.util.Optional;

public interface HospitalRepository {
    List<Hospital> getAllData();
    void save(Hospital hospital);
    Optional<Hospital> findByHospitalCode(String hospitalCode);
    String findHospitalCodeByName(String name);
}
