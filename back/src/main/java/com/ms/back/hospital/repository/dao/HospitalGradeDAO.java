package com.ms.back.hospital.repository.dao;

import com.ms.back.hospital.dto.HospitalGradeRegister;
import com.ms.back.hospital.entity.Hospital;
import com.ms.back.hospital.entity.HospitalGrade;

public record HospitalGradeDAO(
        String hospitalCode,
        Hospital hospital,
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
                hospital,
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
                dto.hospitalCode(),
                dto.hospital(),
                dto.asmGrd01(),
                dto.asmGrd02(),
                dto.asmGrd03(),
                dto.asmGrd04(),
                dto.asmGrd05(),
                dto.asmGrd06(),
                dto.asmGrd07(),
                dto.asmGrd08(),
                dto.asmGrd09(),
                dto.asmGrd10(),
                dto.asmGrd11(),
                dto.asmGrd12(),
                dto.asmGrd13(),
                dto.asmGrd14(),
                dto.asmGrd15(),
                dto.asmGrd16(),
                dto.asmGrd17(),
                dto.asmGrd18(),
                dto.asmGrd19(),
                dto.asmGrd20(),
                dto.asmGrd21(),
                dto.asmGrd22(),
                dto.asmGrd23(),
                dto.asmGrd24()
        );
    }
}
