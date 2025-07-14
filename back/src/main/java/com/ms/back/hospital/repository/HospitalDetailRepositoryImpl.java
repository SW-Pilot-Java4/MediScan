package com.ms.back.hospital.repository;

import com.ms.back.hospital.entity.HospitalDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HospitalDetailRepositoryImpl implements HospitalDetailRepository{
    private final HospitalDetailJPARepository hospitalDetailJPARepository;

    @Override
    public Optional<HospitalDetail> findByHospitalCode(String hospitalCode) {
        return hospitalDetailJPARepository.findById(hospitalCode);
    }
}
