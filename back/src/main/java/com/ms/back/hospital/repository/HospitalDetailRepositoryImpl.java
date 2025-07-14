package com.ms.back.hospital.repository;

import com.ms.back.hospital.entity.HospitalDetail;
import com.ms.back.hospital.repository.dao.HospitalDetailDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HospitalDetailRepositoryImpl implements HospitalDetailRepository{
    private final HospitalDetailJPARepository hospitalDetailJPARepository;

    @Override
    public Optional<HospitalDetailDAO> findByHospitalCode(String hospitalCode) {
      return hospitalDetailJPARepository.findById(hospitalCode).map(HospitalDetailDAO::from);

    }
}
