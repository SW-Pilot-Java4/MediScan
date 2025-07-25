package com.ms.back.hospital.application;

import com.ms.back.hospital.Infrastructure.repository.HospitalCustomRepositoryImpl;
import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;
import com.ms.back.hospital.application.dto.HospitalInfoResponse;
import com.ms.back.hospital.application.dto.HospitalListResponse;
import com.ms.back.hospital.application.port.HospitalDetailDomainService;
import com.ms.back.hospital.application.port.HospitalDomainService;
import com.ms.back.hospital.application.port.HospitalGradeDomainService;
import com.ms.back.hospital.persentation.port.HospitalService;
import com.ms.back.hospital.policy.HospitalPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.*;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HospitalServiceImpl implements HospitalService {

    private final HospitalDomainService hospitalDomainService;
    private final HospitalDetailDomainService hospitalDetailDomainService;
    private final HospitalGradeDomainService hospitalGradeDomainService;
    private final HospitalPolicy hospitalPolicy;
    private final HospitalCustomRepositoryImpl hospitalCustomRepositoryImpl;
    @Override
    public List<HospitalListResponse> getAllHospitalData() {
        return hospitalDomainService.getAllHospitalData().stream()
                .map(HospitalListResponse::from)
                .toList();
    }

    @Override
    public HospitalInfoResponse assembleHospitalInfo(String hospitalCode) {
        HospitalInfoResponse.BaseInfo base = hospitalDomainService.getHospital(hospitalCode);
        HospitalInfoResponse.DetailInfo detail = hospitalDetailDomainService.getHospitalDetail(hospitalCode);
        HospitalInfoResponse.GradeInfo grade = hospitalGradeDomainService.getHospitalGrade(hospitalCode);

        return HospitalInfoResponse.builder()
                .baseInfo(base)
                .detailInfo(detail)
                .gradeInfo(grade)
                .build();
    }

    @Override
    public List<Hospital> searchHospitals(String keyword) {
        return null;
    }

    @Override
    public Page<Hospital> searchHospitals(String name, String address, String callNumber, String categoryCode, Pageable pageable) {
        return hospitalCustomRepositoryImpl.searchByKeyword(name, address, callNumber, categoryCode, pageable);
    }
}
