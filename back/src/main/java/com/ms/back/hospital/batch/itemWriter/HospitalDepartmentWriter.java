package com.ms.back.hospital.batch.itemWriter;

import com.ms.back.hospital.Infrastructure.repository.dao.HospitalDetailDAO;
import jakarta.persistence.EntityManager;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class HospitalDepartmentWriter implements ItemWriter<HospitalDetailDAO> {
    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void write(Chunk<? extends HospitalDetailDAO> chunk) throws Exception {

        for (HospitalDetailDAO dao : chunk) {
            try {
                entityManager.merge(dao.to());
            } catch (Exception e) {
                System.out.println(dao.hospitalCode());
                e.printStackTrace();
            }
        }
    }
}
