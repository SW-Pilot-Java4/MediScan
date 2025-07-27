package com.ms.back.hospital.Infrastructure.repository;

import com.ms.back.hospital.domain.port.HospitalRepository;
import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HospitalRepositoryImpl implements HospitalRepository {
    private final HospitalJPARepository hospitalJPARepository;

    @PersistenceContext
    private EntityManager em;
    @Override
    public List<Hospital> getAllData() {
        return hospitalJPARepository.findAll();
    }

    @Override
    public void save(Hospital hospital) {
        hospitalJPARepository.save(hospital);
    }

    @Override
    public Optional<Hospital> findByHospitalCode(String hospitalCode) {
        return hospitalJPARepository.findByHospitalCode(hospitalCode);
    }

}
