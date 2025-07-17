package com.ms.back.hospital.domain.port;

import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;
import com.ms.back.hospital.Infrastructure.repository.dao.HospitalDAO;

import java.util.List;
import java.util.Optional;

public interface HospitalRepository {
    List<Hospital> getAllData();
    void save(Hospital hospital);
    Optional<HospitalDAO> findByHospitalCode(String hospitalCode);
}
