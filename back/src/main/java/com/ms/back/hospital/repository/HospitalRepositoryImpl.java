package com.ms.back.hospital.repository;

import com.ms.back.hospital.entity.Hospital;
import com.ms.back.hospital.repository.dao.HospitalDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HospitalRepositoryImpl implements HospitalRepository{
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
    public Optional<HospitalDAO> findByHospitalCode(String hospitalCode) {
//        return hospitalJPARepository.findById(hospitalCode).map(HospitalDAO::from);
        return hospitalJPARepository.findByHospitalCode(hospitalCode).map(HospitalDAO::from);
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
