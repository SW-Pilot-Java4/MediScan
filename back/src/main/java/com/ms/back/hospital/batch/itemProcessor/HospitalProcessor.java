package com.ms.back.hospital.batch.itemProcessor;


import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;
import com.ms.back.hospital.batch.dto.HospitalRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HospitalProcessor implements ItemProcessor<HospitalRegister, Hospital> {

    @Override
    public Hospital process(HospitalRegister item) throws Exception {

        return item.to();
    }
}
