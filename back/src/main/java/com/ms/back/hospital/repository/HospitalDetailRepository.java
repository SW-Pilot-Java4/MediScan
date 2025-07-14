package com.ms.back.hospital.repository;

import com.ms.back.hospital.entity.HospitalDetail;
import com.ms.back.hospital.repository.dao.HospitalDetailDAO;


import java.util.Optional;

public interface HospitalDetailRepository {
    Optional<HospitalDetailDAO> findByHospitalCode(String hospitalCode);
}
