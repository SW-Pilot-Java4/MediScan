package com.ms.back.hospital.batch.itemWriter;

import com.ms.back.hospital.Infrastructure.repository.dao.HospitalDAO;
import jakarta.persistence.EntityManager;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HospitalWriter implements ItemWriter<HospitalDAO> {
    @Autowired
    private EntityManager entityManager;

    @Override
    public void write(Chunk<? extends HospitalDAO> chunk) throws Exception {
        for (HospitalDAO dao : chunk) {
            entityManager.persist(dao.to());
        }
    }
}
