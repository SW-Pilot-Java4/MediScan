package com.ms.back.hospital.Infrastructure.repository;

import com.ms.back.hospital.Infrastructure.repository.entity.HospitalGrade;
import com.ms.back.hospital.domain.port.HospitalGradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HospitalGradeRepositoryImpl implements HospitalGradeRepository {
    private final HospitalGradeJPARepository hospitalGradeJPARepository;

    @Override
    public Optional<HospitalGrade> findByHospitalCode(String hospitalCode) {
        return hospitalGradeJPARepository.findByHospitalCode(hospitalCode);
    }
}
