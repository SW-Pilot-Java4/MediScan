package com.ms.back.hospital.batch.itemWriter;

import com.ms.back.hospital.Infrastructure.repository.dao.HospitalGradeDAO;
import jakarta.persistence.EntityManager;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HospitalGradeWriter implements ItemWriter<HospitalGradeDAO> {
    @Autowired
    private EntityManager entityManager;

    @Override
    public void write(Chunk<? extends HospitalGradeDAO> chunk) throws Exception {
        for (HospitalGradeDAO hospitalGradeDAO : chunk) {
            entityManager.persist(hospitalGradeDAO.to());
        }
    }
}
