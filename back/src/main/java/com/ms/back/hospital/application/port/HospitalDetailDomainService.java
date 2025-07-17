package com.ms.back.hospital.application.port;

import com.ms.back.hospital.application.dto.HospitalInfoResponse.DetailInfo;

import java.util.List;

public interface HospitalDetailDomainService {

    DetailInfo getHospitalDetail(String hospitalCode);
}
