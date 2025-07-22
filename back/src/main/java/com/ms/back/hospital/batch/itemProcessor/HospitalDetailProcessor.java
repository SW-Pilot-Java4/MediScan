package com.ms.back.hospital.batch.itemProcessor;

import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;
import com.ms.back.hospital.Infrastructure.repository.entity.HospitalDetail;
import com.ms.back.hospital.batch.dto.HospitalDetailRegister;
import com.ms.back.hospital.domain.port.HospitalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class HospitalDetailProcessor implements ItemProcessor<HospitalDetailRegister, HospitalDetail> {
    private final HospitalRepository hospitalRepository;

    @Override
    public HospitalDetail process(HospitalDetailRegister item) throws Exception {
        try {
            return item.to();
        }catch (RuntimeException e) {
            // 존재하지 않는 Hospital Code가 있을 경우 해당 데이터는 Drop
            log.error("등록되지 않은 HospitalCode"+item.hospitalCode());
            return null;
        }

    }

    private Hospital validationData(String hospitalCode) {
        Optional<Hospital> dao = hospitalRepository.findByHospitalCode(hospitalCode);
        return dao.orElseThrow(RuntimeException :: new);
    }
}
