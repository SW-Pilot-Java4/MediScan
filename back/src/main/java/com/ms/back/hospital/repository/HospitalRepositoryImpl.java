package com.ms.back.hospital.repository;

import com.ms.back.hospital.entity.Hospital;
import com.ms.back.hospital.repository.dao.HospitalDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HospitalRepositoryImpl implements HospitalRepository{
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
    public Optional<HospitalDAO> findByHospitalCode(String hospitalCode) {
        return hospitalJPARepository.findById(hospitalCode).map(HospitalDAO::from);
    }

}
