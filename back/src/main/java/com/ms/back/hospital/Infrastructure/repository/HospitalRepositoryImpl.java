package com.ms.back.hospital.Infrastructure.repository;

import com.ms.back.hospital.domain.port.HospitalRepository;
import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HospitalRepositoryImpl implements HospitalRepository {
    private final HospitalJPARepository hospitalJPARepository;

    @Override
    public List<Hospital> getAllData() {
        return hospitalJPARepository.findAll();
    }

    @Override
    public void save(Hospital hospital) {
        hospitalJPARepository.save(hospital);
    }

    @Override
    public Optional<Hospital> findByHospitalCode(String hospitalCode) {
        return hospitalJPARepository.findByHospitalCode(hospitalCode);
    }
}
