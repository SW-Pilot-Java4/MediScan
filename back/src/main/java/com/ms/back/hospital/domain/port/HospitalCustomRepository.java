package com.ms.back.hospital.domain.port;

import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;
import org.springframework.data.domain.*;
import java.util.List;

public interface HospitalCustomRepository {
    Page<Hospital> searchByKeyword(String hname,String haddress, String hnum,String catcode,Pageable pageable);
}
