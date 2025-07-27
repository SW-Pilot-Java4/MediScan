package com.ms.back.hospital.batch.itemProcessor;

import com.ms.back.hospital.Infrastructure.repository.entity.HospitalDetail;
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
    private final HospitalDetailRepository hospitalDetailRepository;
    private final HospitalRepository hospitalRepository;


    @Override
    public HospitalDetail process(HospitalCodeWithDepartments item) throws Exception {
        try {
            HospitalDetail entity;
            try {
                // 1. 기존 hospitalCode로 조회 (validationData 사용)
                entity = validationData(item.getHospitalCode());
            } catch (RuntimeException e) {
                // 2. hospitalName으로 hospitalCode 재조회
                String newHospitalCode = getHospitalCodeByHospitalName(item.getHospitalName());

                if ("exit".equals(newHospitalCode)) {
                    log.warn("중복 병원명으로 인해 처리 중단: {}", item.getHospitalName());
                    return null; // 데이터 drop
                }

                // 3. 새 hospitalCode로 HospitalDetail 조회 (validationData 사용)
                entity = validationData(newHospitalCode);
            }

            // 4. 부서 코드 업데이트 (set 방식에서 merge 방식으로 변경)
            entity.addDepartmentCodes(item.getDepartmentCodes());
            return entity;

        } catch (RuntimeException e) {
            log.error("등록되지 않은 HospitalCode: {}", item.getHospitalCode());
            return null;
        }
    }

    private HospitalDetail validationData(String hospitalCode) {
        return hospitalDetailRepository.findByHospitalCode(hospitalCode)
                .orElseThrow(RuntimeException::new);
    }

    private String getHospitalCodeByHospitalName(String hospitalName) {
        log.warn("등롣하지 않은 정보 : "+hospitalName);
        return hospitalRepository.findHospitalCodeByName(hospitalName);
    }
}