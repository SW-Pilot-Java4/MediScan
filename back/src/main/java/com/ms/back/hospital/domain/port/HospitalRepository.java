package com.ms.back.hospital.domain.port;

import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface HospitalRepository {
    List<Hospital> getAllData();
    Optional<Hospital> findByHospitalCode(String hospitalCode);
    List<Hospital> findHospitalsByLatLngInt(int latInt, int lngInt);
    Page<Hospital> searchByKeyword(String hname, String haddress, String hnum, String catcode, Pageable pageable);
}

