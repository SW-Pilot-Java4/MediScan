package com.ms.back.hospital.domain.port;


import com.ms.back.hospital.Infrastructure.repository.entity.HospitalDetail;

import java.util.Optional;

public interface HospitalDetailRepository {
    Optional<HospitalDetail> findByHospitalCode(String hospitalCode);
}
