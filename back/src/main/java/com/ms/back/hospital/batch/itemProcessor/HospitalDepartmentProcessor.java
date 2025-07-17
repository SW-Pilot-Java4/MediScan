package com.ms.back.hospital.batch.itemProcessor;

import com.ms.back.hospital.Infrastructure.repository.entity.HospitalDetail;
import com.ms.back.hospital.batch.dto.HospitalCodeWithDepartments;
import com.ms.back.hospital.domain.port.HospitalDetailRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class HospitalDepartmentProcessor implements ItemProcessor<HospitalCodeWithDepartments, HospitalDetail> {
    private final HospitalDetailRepository hospitalDetailRepository;

    @Override
    public HospitalDetail process(HospitalCodeWithDepartments item) throws Exception {
        try {
            HospitalDetail entity = validationData(item.getHospitalCode());
//            dao = dao.setDepartmentCodes(dao, item.getDepartmentCodes());
            entity.setDepartmentCodes(item.getDepartmentCodes());
            return entity;
        }catch (RuntimeException e) {
            log.error("등록되지 않은 HospitalCode"+item.getHospitalCode());
            return null;
        }
    }

    private HospitalDetail validationData(String hospitalCode) {
        Optional<HospitalDetail> entity = hospitalDetailRepository.findByHospitalCode(hospitalCode);
        return entity.orElseThrow(RuntimeException::new);
    }


}
