package com.ms.back.hospital.repository;

import com.ms.back.hospital.entity.HospitalDetail;


import java.util.Optional;

public interface HospitalDetailRepository {
    Optional<HospitalDetail> findByHospitalCode(String hospitalCode);
}
