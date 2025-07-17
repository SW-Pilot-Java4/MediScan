package com.ms.back.hospital.domain.port;

import com.ms.back.hospital.Infrastructure.repository.entity.HospitalGrade;

import java.util.Optional;

public interface HospitalGradeRepository {
    Optional<HospitalGrade> findByHospitalCode(String hospitalCode);
}
