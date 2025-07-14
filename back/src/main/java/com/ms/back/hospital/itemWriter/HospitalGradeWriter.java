package com.ms.back.hospital.itemWriter;

import com.ms.back.hospital.entity.Hospital;
import com.ms.back.hospital.entity.HospitalGrade;
import com.ms.back.hospital.repository.HospitalRepository;
import jakarta.persistence.EntityManager;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HospitalGradeWriter implements ItemWriter<HospitalGrade> {
    @Autowired
    private EntityManager entityManager;

    @Override
    public void write(Chunk<? extends HospitalGrade> chunk) throws Exception {
        for (HospitalGrade hospitalGrade : chunk) {
            entityManager.persist(hospitalGrade);
        }
    }
}
