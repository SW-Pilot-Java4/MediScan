package com.ms.back.hospital.Infrastructure.repository.dao;

import com.ms.back.hospital.batch.dto.HospitalGradeRegister;
import com.ms.back.hospital.Infrastructure.repository.entity.HospitalGrade;

public record HospitalGradeDAO(
        String hospitalCode,
        String asmGrd01,
        String asmGrd02,
        String asmGrd03,
        String asmGrd04,
        String asmGrd05,
        String asmGrd06,
        String asmGrd07,
        String asmGrd08,
        String asmGrd09,
        String asmGrd10,
        String asmGrd11,
        String asmGrd12,
        String asmGrd13,
        String asmGrd14,
        String asmGrd15,
        String asmGrd16,
        String asmGrd17,
        String asmGrd18,
        String asmGrd19,
        String asmGrd20,
        String asmGrd21,
        String asmGrd22,
        String asmGrd23,
        String asmGrd24
) {
    public HospitalGrade to() {
        return HospitalGrade.create(
                hospitalCode,
                asmGrd01,
                asmGrd02,
                asmGrd03,
                asmGrd04,
                asmGrd05,
                asmGrd06,
                asmGrd07,
                asmGrd08,
                asmGrd09,
                asmGrd10,
                asmGrd11,
                asmGrd12,
                asmGrd13,
                asmGrd14,
                asmGrd15,
                asmGrd16,
                asmGrd17,
                asmGrd18,
                asmGrd19,
                asmGrd20,
                asmGrd21,
                asmGrd22,
                asmGrd23,
                asmGrd24
        );
    }

    public static HospitalGradeDAO from(HospitalGradeRegister dto) {
        return new HospitalGradeDAO(
                dto.getHospitalCode(),
                dto.getAsmGrd01(),
                dto.getAsmGrd02(),
                dto.getAsmGrd03(),
                dto.getAsmGrd04(),
                dto.getAsmGrd05(),
                dto.getAsmGrd06(),
                dto.getAsmGrd07(),
                dto.getAsmGrd08(),
                dto.getAsmGrd09(),
                dto.getAsmGrd10(),
                dto.getAsmGrd11(),
                dto.getAsmGrd12(),
                dto.getAsmGrd13(),
                dto.getAsmGrd14(),
                dto.getAsmGrd15(),
                dto.getAsmGrd16(),
                dto.getAsmGrd17(),
                dto.getAsmGrd18(),
                dto.getAsmGrd19(),
                dto.getAsmGrd20(),
                dto.getAsmGrd21(),
                dto.getAsmGrd22(),
                dto.getAsmGrd23(),
                dto.getAsmGrd24()
        );
    }
}
