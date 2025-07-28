package com.ms.back.hospital.persentation.port;

import com.ms.back.global.apiResponse.PageResponse;
import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;
import com.ms.back.hospital.application.dto.HospitalCategoryCode;
import com.ms.back.hospital.application.dto.HospitalInfoResponse;
import com.ms.back.hospital.application.dto.HospitalListResponse;
import com.ms.back.hospital.batch.dto.HospitalDto;
import org.springframework.data.domain.*;
import java.util.List;

public interface HospitalService {
    List<HospitalListResponse> getAllHospitalData();
    HospitalInfoResponse assembleHospitalInfo(String hospitalCode);
    List<HospitalListResponse> getHospitalsNearby(String lat, String lng, double distanceKm);
    PageResponse<HospitalDto> getHospitalsByKeyword(String name, String address, String callNumber, String categoryCode, Pageable pageable);
}
