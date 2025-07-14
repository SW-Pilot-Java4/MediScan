package com.ms.back.hospital.itemWriter;

import com.ms.back.hospital.repository.dao.HospitalDetailDAO;
import jakarta.persistence.EntityManager;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class HospitalDetailWriter implements ItemWriter<HospitalDetailDAO> {
    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void write(Chunk<? extends HospitalDetailDAO> chunk) throws Exception {
        try {
            for (HospitalDetailDAO dao : chunk) {
                entityManager.persist(dao.to());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
