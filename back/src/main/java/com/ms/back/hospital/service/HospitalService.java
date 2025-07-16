package com.ms.back.hospital.service;

import com.ms.back.hospital.entity.Hospital;
import com.ms.back.hospital.repository.HospitalRepository;
import com.ms.back.hospital.repository.HospitalRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HospitalService {
    private final HospitalRepository hospitalRepository;

    public List<Hospital> getAllData() {
        return hospitalRepository.getAllData();
    }
}
