package com.ms.back.hospital.itemWriter;

import com.ms.back.hospital.entity.HospitalDetail;
import com.ms.back.hospital.entity.HospitalGrade;
import jakarta.persistence.EntityManager;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HospitalDetailWriter implements ItemWriter<HospitalDetail> {
    @Autowired
    private EntityManager entityManager;

    @Override
    public void write(Chunk<? extends HospitalDetail> chunk) throws Exception {
        for (HospitalDetail hospitalDetail : chunk) {
            entityManager.persist(hospitalDetail);
        }
    }
}
