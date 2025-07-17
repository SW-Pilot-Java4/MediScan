package com.ms.back.hospital.domain.service;

import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;
import com.ms.back.hospital.application.dto.HospitalInfoResponse.BaseInfo;
import com.ms.back.hospital.application.port.HospitalDomainService;
import com.ms.back.hospital.domain.port.HospitalRepository;
import com.ms.back.hospital.exception.HospitalException.HospitalNotExist;
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

    @Override
    public BaseInfo getHospital(String hospitalCode) {
        Hospital hospital = findHospitalByCode(hospitalCode);

        return BaseInfo.builder()
                .hospitalCode(hospital.getHospitalCode())
                .name(hospital.getName())
                .address(hospital.getAddress())
                .callNumber(hospital.getCallNumber())
                .latitude(hospital.getLatitude())
                .longitude(hospital.getLongitude())
                .build();
    }

    private Hospital findHospitalByCode(String hospitalCode) {
        return hospitalRepository.findByHospitalCode(hospitalCode)
                .orElseThrow(HospitalNotExist::new);
    }
}
