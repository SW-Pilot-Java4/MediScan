package com.ms.back.hospital.fieldSetMapper;

import com.ms.back.hospital.dto.HospitalDetailRegister;
import com.ms.back.hospital.entity.HospitalDetail;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class HospitalDetailFieldSetMapper implements FieldSetMapper<HospitalDetailRegister> {
    @Override
    public HospitalDetailRegister mapFieldSet(FieldSet fieldSet) throws BindException {
        if (fieldSet == null) return null;

        HospitalDetailRegister dto = new HospitalDetailRegister(
                fieldSet.readRawString(0),
                null,
                null,
                fieldSet.readRawString(8),
                fieldSet.readRawString(9),
                fieldSet.readRawString(10),
                fieldSet.readRawString(11),
                fieldSet.readRawString(12),
                fieldSet.readRawString(13),
                fieldSet.readRawString(14),
                fieldSet.readRawString(15),
                fieldSet.readRawString(16),
                fieldSet.readRawString(17),
                fieldSet.readRawString(18),
                fieldSet.readRawString(19),
                fieldSet.readRawString(20),
                fieldSet.readRawString(21),
                fieldSet.readRawString(22),
                fieldSet.readRawString(23),
                fieldSet.readRawString(24),
                fieldSet.readRawString(25),
                fieldSet.readRawString(26),
                fieldSet.readRawString(27),
                fieldSet.readRawString(28),
                fieldSet.readRawString(29),
                fieldSet.readRawString(30),
                fieldSet.readRawString(31),
                fieldSet.readRawString(32),
                fieldSet.readRawString(33)
        );
        return dto;
    }


}
