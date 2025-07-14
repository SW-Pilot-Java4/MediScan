package com.ms.back.hospital.itemWriter;

import com.ms.back.hospital.entity.Hospital;
import com.ms.back.hospital.entity.HospitalGrade;
import com.ms.back.hospital.repository.dao.HospitalRegisterDAO;
import jakarta.persistence.EntityManager;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HospitalWriter implements ItemWriter<HospitalRegisterDAO> {
    @Autowired
    private EntityManager entityManager;

    @Override
    public void write(Chunk<? extends HospitalRegisterDAO> chunk) throws Exception {
        for (HospitalRegisterDAO dao : chunk) {
            entityManager.persist(dao.to());
        }
    }
}
