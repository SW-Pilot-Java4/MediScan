package com.ms.back.hospital.itemProcessor;

import com.ms.back.hospital.entity.Hospital;
import com.ms.back.hospital.entity.HospitalGrade;
import com.ms.back.hospital.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HospitalProcessor implements ItemProcessor<Hospital, Hospital> {

    @Override
    public Hospital process(Hospital item) throws Exception {

        return item;
    }
}
