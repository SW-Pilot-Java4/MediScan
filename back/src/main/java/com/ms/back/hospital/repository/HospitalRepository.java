package com.ms.back.hospital.repository;

import com.ms.back.hospital.entity.Hospital;

import java.util.List;

public interface HospitalRepository {
    List<Hospital> getAllData();
    void save(Hospital hospital);
}
