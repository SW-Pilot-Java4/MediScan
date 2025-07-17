package com.ms.back.hospital.domain.service;

import com.ms.back.hospital.Infrastructure.repository.entity.HospitalGrade;
import com.ms.back.hospital.application.dto.HospitalInfoResponse.GradeInfo;
import com.ms.back.hospital.application.port.HospitalGradeDomainService;
import com.ms.back.hospital.domain.port.HospitalGradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HospitalGradeDomainServiceImpl implements HospitalGradeDomainService {
    private final HospitalGradeRepository hospitalGradeRepository;

    @Override
    public GradeInfo getHospitalGrade(String hospitalCode) {
        HospitalGrade grade = hospitalGradeRepository.findByHospitalCode(hospitalCode)
                .orElse(null);

        if (grade == null) return null;
        return GradeInfo.builder()
                .grades(extractGrades(grade))
                .build();
    }

    private Map<String, Long> extractGrades(HospitalGrade grade) {
        Map<String, Long> grades = new LinkedHashMap<>();

        for (int i = 1; i <= 24; i++) {
            String key = String.format("asmGrd%02d", i);
            String value = getGradeValue(grade, key);
            grades.put(key, convertGrade(value));
        }
        return grades;
    }

    private String getGradeValue(HospitalGrade grade, String fieldName) {
        try {
            Field field = HospitalGrade.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (String) field.get(grade);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Invalid grade field: " + fieldName, e);
        }
    }

    private Long convertGrade(String gradeStr) {
        if (gradeStr == null) return null;
        if (gradeStr.equals("등급 제외")) return 0L;

        try {
            return Long.parseLong(gradeStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid grade string: " + gradeStr);
        }
    }
}
