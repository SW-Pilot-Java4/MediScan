package com.ms.back.hospital.application;

import com.ms.back.hospital.Infrastructure.repository.HospitalCustomRepositoryImpl;
import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;
import com.ms.back.hospital.application.dto.HospitalCategoryCode;
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

    @Override
    public List<HospitalCategoryCode> getHospitalCategoryCodes() {
        return List.of(
                new HospitalCategoryCode("01", "종합전문병원"),
                new HospitalCategoryCode("11", "종합병원"),
                new HospitalCategoryCode("21", "병원"),
                new HospitalCategoryCode("28", "요양병원"),
                new HospitalCategoryCode("29", "정신병원"),
                new HospitalCategoryCode("31", "의원"),
                new HospitalCategoryCode("41", "치과병원"),
                new HospitalCategoryCode("51", "치과의원"),
                new HospitalCategoryCode("71", "보건소"),
                new HospitalCategoryCode("72", "보건지소"),
                new HospitalCategoryCode("73", "보건진료소"),
                new HospitalCategoryCode("92", "한방병원"),
                new HospitalCategoryCode("93", "한의원")
        );
    }
}
