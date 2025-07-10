package com.ms.back.hospital.repository;

import com.ms.back.hospital.entity.Hospital;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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

}
