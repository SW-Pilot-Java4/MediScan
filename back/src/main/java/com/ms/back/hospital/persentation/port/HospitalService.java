package com.ms.back.hospital.persentation.port;

import com.ms.back.hospital.application.dto.HospitalInfoResponse;
import com.ms.back.hospital.application.dto.HospitalListResponse;

import java.util.List;

public interface HospitalService {
    List<HospitalListResponse> getAllHospitalData();
    HospitalInfoResponse assembleHospitalInfo(String hospitalCode);
}
