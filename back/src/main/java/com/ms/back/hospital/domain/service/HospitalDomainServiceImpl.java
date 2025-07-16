package com.ms.back.hospital.domain.service;

import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;
import com.ms.back.hospital.application.port.HospitalDomainService;
import com.ms.back.hospital.domain.port.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HospitalDomainServiceImpl implements HospitalDomainService {
    private final HospitalRepository hospitalRepository;

    @Override
    public List<Hospital> getAllHospitalData() {
        return hospitalRepository.getAllData();
    }
}
