package com.ms.back.hospital.itemProcessor;

import com.ms.back.hospital.dto.HospitalRegister;
import com.ms.back.hospital.entity.Hospital;
import com.ms.back.hospital.entity.HospitalGrade;
import com.ms.back.hospital.repository.HospitalRepository;
import com.ms.back.hospital.repository.dao.HospitalRegisterDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HospitalProcessor implements ItemProcessor<HospitalRegister, HospitalRegisterDAO> {

    @Override
    public HospitalRegisterDAO process(HospitalRegister item) throws Exception {

        return HospitalRegisterDAO.from(item);
    }
}
