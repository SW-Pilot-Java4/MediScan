package com.ms.back.hospital.batch.itemProcessor;

import com.ms.back.hospital.batch.dto.HospitalGradeRegister;
import com.ms.back.hospital.domain.port.HospitalRepository;
import com.ms.back.hospital.Infrastructure.repository.dao.HospitalDAO;
import com.ms.back.hospital.Infrastructure.repository.dao.HospitalGradeDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class HospitalGradeProcessor implements ItemProcessor<HospitalGradeRegister, HospitalGradeDAO> {
    private final HospitalRepository hospitalRepository;

    @Override
    public HospitalGradeDAO process(HospitalGradeRegister item) throws Exception {
        try {
//            item = item.setHospital(item, validationData(item.getHospitalCode()).to());
            return HospitalGradeDAO.from(item);
        } catch (RuntimeException e) {
            // 존재하지 않는 Hospital Code가 있을 경우 해당 데이터는 Drop
            log.error("등록되지 않은 HospitalCode"+item.getHospitalCode());
            return null;
        }
    }

    private HospitalDAO validationData(String hospitalCode) {
        Optional<HospitalDAO> dao = hospitalRepository.findByHospitalCode(hospitalCode);
        return dao.orElseThrow(RuntimeException :: new);
    }
}
