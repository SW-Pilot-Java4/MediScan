package com.ms.back.hospital.persentation.port;

import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;
import com.ms.back.hospital.application.dto.HospitalCategoryCode;
import com.ms.back.hospital.application.dto.HospitalInfoResponse;
import com.ms.back.hospital.application.dto.HospitalListResponse;
import org.springframework.data.domain.*;
import java.util.List;

public interface HospitalService {
    List<HospitalListResponse> getAllHospitalData();
    HospitalInfoResponse assembleHospitalInfo(String hospitalCode);
    List<HospitalListResponse> getHospitalsNearby(String lat, String lng, double distanceKm);
    List<Hospital> searchHospitals(String keyword);
    Page<Hospital> searchHospitals(String name, String address, String callNumber, String categoryCode, Pageable pageable);
    List<HospitalCategoryCode> getHospitalCategoryCodes();
}
