package com.ms.back.hospital.itemProcessor;

import com.ms.back.hospital.dto.HospitalGradeRegister;
import com.ms.back.hospital.entity.Hospital;
import com.ms.back.hospital.entity.HospitalGrade;
import com.ms.back.hospital.repository.HospitalRepository;
import com.ms.back.hospital.repository.dao.HospitalDAO;
import com.ms.back.hospital.repository.dao.HospitalGradeDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
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
            item.setHospital(validationData(item.hospitalCode()).to());
            return HospitalGradeDAO.from(item);
        } catch (RuntimeException e) {
            // 존재하지 않는 Hospital Code가 있을 경우 해당 데이터는 Drop
            log.error("등록되지 않은 HospitalCode"+item.hospitalCode());
            return null;
        }
    }

    private HospitalDAO validationData(String hospitalCode) {
        Optional<HospitalDAO> dao = hospitalRepository.findByHospitalCode(hospitalCode);
        return dao.orElseThrow(RuntimeException :: new);
    }
}
