package com.ms.back.hospital.dto;

import com.ms.back.hospital.entity.Hospital;

public record HospitalGradeRegister(
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

    public void setHospital(Hospital hospital) {
        hospital = hospital;
    }
}
