package com.ms.back.hospital.repository;

import com.ms.back.hospital.entity.Hospital;

import java.util.List;
import java.util.Optional;

public interface HospitalRepository {
    List<Hospital> getAllData();
    void save(Hospital hospital);
    Optional<Hospital> findByHospitalCode(String hospitalCode);
}
