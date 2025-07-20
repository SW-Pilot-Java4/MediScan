package com.ms.back.hospital.domain.port;

import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;

import java.util.List;

public interface HospitalCustomRepository {
    List<Hospital> searchByKeyword(String hname,String haddress, String hnum,String catcode);
}
