package com.ms.back.hospital.application.port;

import com.ms.back.hospital.application.dto.HospitalInfoResponse.GradeInfo;

import java.util.List;

public interface HospitalGradeDomainService {
    GradeInfo getHospitalGrade(String hospitalCode);
}
