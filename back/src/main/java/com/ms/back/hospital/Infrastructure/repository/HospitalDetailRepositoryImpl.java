package com.ms.back.hospital.Infrastructure.repository;

import com.ms.back.hospital.Infrastructure.repository.dao.HospitalDetailDAO;
import com.ms.back.hospital.domain.port.HospitalDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HospitalDetailRepositoryImpl implements HospitalDetailRepository {
    private final HospitalDetailJPARepository hospitalDetailJPARepository;

    @Override
    public Optional<HospitalDetailDAO> findByHospitalCode(String hospitalCode) {
      return hospitalDetailJPARepository.findByHospitalCode(hospitalCode).map(HospitalDetailDAO::from);

    }
}
