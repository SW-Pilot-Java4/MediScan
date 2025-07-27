package com.ms.back.hospital.batch.fieldSetMapper;

import com.ms.back.hospital.batch.dto.HospitalDepartmentRow;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class HospitalDepartmentFieldSetMapper implements FieldSetMapper<HospitalDepartmentRow> {
    @Override
    public HospitalDepartmentRow mapFieldSet(FieldSet fieldSet) throws BindException {
        if (fieldSet == null) return null;
        return new HospitalDepartmentRow(
                fieldSet.readString("hospitalCode"),
                fieldSet.readString("hospitalName"),
                fieldSet.readString("departmentCode")
        );
    }
}
