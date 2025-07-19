package com.ms.back.hospital.batch.itemWriter;


import com.ms.back.hospital.Infrastructure.repository.entity.HospitalDetail;
import jakarta.persistence.EntityManager;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class HospitalDepartmentWriter implements ItemWriter<HospitalDetail> {
    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void write(Chunk<? extends HospitalDetail> chunk) throws Exception {

        for (HospitalDetail entity : chunk) {
            try {
                entityManager.merge(entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
