package com.ms.back.hospital.Infrastructure.repository;

import com.ms.back.hospital.domain.port.HospitalRepository;
import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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

    @Override
    public List<Hospital> searchByKeyword(String keyword) {
        return em.createQuery(
                        "SELECT h FROM Hospital h " +
                                "WHERE LOWER(h.name) LIKE LOWER(:keyword) " +
                                "OR LOWER(h.address) LIKE LOWER(:keyword) " +
                                "OR LOWER(h.callNumber) LIKE LOWER(:keyword)",
                        Hospital.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getResultList();
    }
}
