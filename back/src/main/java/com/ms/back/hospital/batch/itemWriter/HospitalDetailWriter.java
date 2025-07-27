package com.ms.back.hospital.batch.itemWriter;

import com.ms.back.hospital.Infrastructure.repository.entity.HospitalDetail;
import com.ms.back.hospital.batch.component.HospitalDetailCache;
import jakarta.persistence.EntityManager;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class HospitalDetailWriter implements ItemWriter<HospitalDetail> {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private HospitalDetailCache cache;

    @Override
    @Transactional
    public void write(Chunk<? extends HospitalDetail> chunk) throws Exception {
        try {
            for (HospitalDetail entity : chunk) {
                entityManager.persist(entity);
                cache.put(entity);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
