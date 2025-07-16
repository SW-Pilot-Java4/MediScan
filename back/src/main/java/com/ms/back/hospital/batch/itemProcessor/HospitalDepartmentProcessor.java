package com.ms.back.hospital.batch.itemProcessor;

import com.ms.back.hospital.batch.dto.HospitalCodeWithDepartments;
import com.ms.back.hospital.domain.port.HospitalDetailRepository;
import com.ms.back.hospital.Infrastructure.repository.dao.HospitalDetailDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class HospitalDepartmentProcessor implements ItemProcessor<HospitalCodeWithDepartments, HospitalDetailDAO> {
    private final HospitalDetailRepository hospitalDetailRepository;

    @Override
    public HospitalDetailDAO process(HospitalCodeWithDepartments item) throws Exception {
        try {
            HospitalDetailDAO dao = validationData(item.getHospitalCode());
            dao = dao.setDepartmentCodes(dao, item.getDepartmentCodes());
            return dao;
        }catch (RuntimeException e) {
            log.error("등록되지 않은 HospitalCode"+item.getHospitalCode());
            return null;
        }
    }

    private HospitalDetailDAO validationData(String hospitalCode) {
        Optional<HospitalDetailDAO> dao = hospitalDetailRepository.findByHospitalCode(hospitalCode);
        return dao.orElseThrow(RuntimeException :: new);
    }


}
