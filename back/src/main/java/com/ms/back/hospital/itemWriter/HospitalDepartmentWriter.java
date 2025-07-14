package com.ms.back.hospital.itemWriter;

import com.ms.back.hospital.entity.HospitalDetail;
import com.ms.back.hospital.repository.dao.HospitalDetailDAO;
import jakarta.persistence.EntityManager;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HospitalDepartmentWriter implements ItemWriter<HospitalDetailDAO> {
    @Autowired
    private EntityManager entityManager;

    @Override
    public void write(Chunk<? extends HospitalDetailDAO> chunk) throws Exception {
        for (HospitalDetailDAO dao : chunk) {
            entityManager.merge(dao);
        }
    }
}
