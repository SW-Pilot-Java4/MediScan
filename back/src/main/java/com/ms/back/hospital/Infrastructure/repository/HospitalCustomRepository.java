package com.ms.back.hospital.Infrastructure.repository;

import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;
import org.springframework.data.domain.*;
import java.util.List;

public interface HospitalCustomRepository {
    Page<Hospital> searchByKeyword(String name, String address, String callNumber, String categoryCode, Pageable pageable);
}
