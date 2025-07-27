package com.ms.back.hospital.batch.itemProcessor;

import com.ms.back.hospital.Infrastructure.repository.entity.HospitalDetail;
import com.ms.back.hospital.batch.component.HospitalDetailCache;
import com.ms.back.hospital.batch.dto.HospitalCodeWithDepartments;
import com.ms.back.hospital.domain.port.HospitalDetailRepository;
import com.ms.back.hospital.domain.port.HospitalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class HospitalDepartmentProcessor implements ItemProcessor<HospitalCodeWithDepartments, HospitalDetail> {
//    private final HospitalDetailRepository hospitalDetailRepository;
//    private final HospitalRepository hospitalRepository;
    private final HospitalDetailCache cache;

    @Override
    public HospitalDetail process(HospitalCodeWithDepartments item) throws Exception {
        return cache.get(item.getHospitalCode())
                .map(hospitalDetail -> {
                    // 진료과 정보 병합
                    hospitalDetail.addDepartmentCodes(item.getDepartmentCodes());
                    return hospitalDetail;
                })
                .orElse(null);
    }

//    private HospitalDetail validationData(String hospitalCode) {
//        return hospitalDetailRepository.findByHospitalCode(hospitalCode)
//                .orElseThrow(RuntimeException::new);
//    }
//
//    private String getHospitalCodeByHospitalName(String hospitalName) {
//        log.warn("등롣하지 않은 정보 : "+hospitalName);
//        return hospitalRepository.findHospitalCodeByName(hospitalName);
//    }
}