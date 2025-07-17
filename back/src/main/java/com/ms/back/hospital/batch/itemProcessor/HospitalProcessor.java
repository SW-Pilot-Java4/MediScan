package com.ms.back.hospital.batch.itemProcessor;

import com.ms.back.hospital.batch.dto.HospitalRegister;
import com.ms.back.hospital.Infrastructure.repository.dao.HospitalDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HospitalProcessor implements ItemProcessor<HospitalRegister, HospitalDAO> {

    @Override
    public HospitalDAO process(HospitalRegister item) throws Exception {

        return HospitalDAO.from(item);
    }
}
