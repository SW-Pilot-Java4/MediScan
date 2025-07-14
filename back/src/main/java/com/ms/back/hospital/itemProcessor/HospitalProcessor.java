package com.ms.back.hospital.itemProcessor;

import com.ms.back.hospital.dto.HospitalRegister;
import com.ms.back.hospital.repository.dao.HospitalDAO;
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
