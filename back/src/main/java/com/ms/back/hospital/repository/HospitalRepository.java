package com.ms.back.hospital.repository;

import com.ms.back.hospital.entity.Hospital;
import com.ms.back.hospital.repository.dao.HospitalDAO;

import java.util.List;
import java.util.Optional;

public interface HospitalRepository {
    List<Hospital> getAllData();
    void save(Hospital hospital);
    Optional<HospitalDAO> findByHospitalCode(String hospitalCode);
}
