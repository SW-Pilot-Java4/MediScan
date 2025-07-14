package com.ms.back.hospital.itemProcessor;

import com.ms.back.hospital.dto.HospitalCodeWithDepartments;
import com.ms.back.hospital.entity.Hospital;
import com.ms.back.hospital.entity.HospitalDetail;
import com.ms.back.hospital.repository.HospitalDetailRepository;
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
        HospitalDetail hospitalDetail;
        try {
            hospitalDetail = validationData(item.getHospitalCode());
        }catch (RuntimeException e) {
            hospitalDetail=null;
            log.error("등록되지 않은 HospitalCode"+item.getHospitalCode());
            return null;
        }

        hospitalDetail.setDepartmentCodes(item.getDepartmentCodes());

        return hospitalDetail;
    }

    private HospitalDetail validationData(String hospitalCode) {
        Optional<HospitalDetail> hospitalDetail = hospitalDetailRepository.findByHospitalCode(hospitalCode);
        return hospitalDetail.orElseThrow(RuntimeException :: new);
    }


}
