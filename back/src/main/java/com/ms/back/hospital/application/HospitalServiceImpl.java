package com.ms.back.hospital.application;

import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;
import com.ms.back.hospital.application.dto.HospitalListResponse;
import com.ms.back.hospital.application.port.HospitalDomainService;
import com.ms.back.hospital.persentation.port.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HospitalServiceImpl implements HospitalService {

    private final HospitalDomainService hospitalDomainService;
    @Override
    public List<HospitalListResponse> getAllHospitalData() {
        return hospitalDomainService.getAllHospitalData().stream()
                .map(HospitalListResponse::from)
                .toList();
    }
}
