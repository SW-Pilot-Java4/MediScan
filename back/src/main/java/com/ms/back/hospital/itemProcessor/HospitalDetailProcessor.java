package com.ms.back.hospital.itemProcessor;

import com.ms.back.hospital.entity.Hospital;
import com.ms.back.hospital.entity.HospitalDetail;
import com.ms.back.hospital.entity.HospitalGrade;
import com.ms.back.hospital.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class HospitalDetailProcessor implements ItemProcessor<HospitalDetail, HospitalDetail> {
    private final HospitalRepository hospitalRepository;

    @Override
    public HospitalDetail process(HospitalDetail item) throws Exception {
        System.out.println(item.getHospitalCode());
        try {
            item.setHospital(validationData(item.getHospitalCode()));
        }catch (RuntimeException e) {
            // 존재하지 않는 Hospital Code가 있을 경우 해당 데이터는 Drop
            log.error("등록되지 않은 HospitalCode"+item.getHospitalCode());
            return null;
        }
        return item;
    }

    private Hospital validationData(String hospitalCode) {
        Optional<Hospital> hospital = hospitalRepository.findByHospitalCode(hospitalCode);
        return hospital.orElseThrow(RuntimeException :: new);
    }
}
