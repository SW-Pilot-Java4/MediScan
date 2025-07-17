package com.ms.back.hospital.domain.port;

import com.ms.back.hospital.Infrastructure.repository.dao.HospitalDetailDAO;


import java.util.Optional;

public interface HospitalDetailRepository {
    Optional<HospitalDetailDAO> findByHospitalCode(String hospitalCode);
}
