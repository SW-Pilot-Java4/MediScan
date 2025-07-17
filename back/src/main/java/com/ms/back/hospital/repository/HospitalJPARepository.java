package com.ms.back.hospital.repository;

import com.ms.back.hospital.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public interface HospitalJPARepository extends JpaRepository<Hospital, String> {

    Optional<Hospital> findByHospitalCode(String hospitalCode);
    List<Hospital> findByNameContainingIgnoreCaseOrAddressContainingIgnoreCaseOrCallNumberContainingIgnoreCase(
            String name, String address, String callNumber
    );
}
