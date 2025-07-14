package com.ms.back.hospital.dto;

import com.ms.back.hospital.entity.Hospital;
import lombok.Data;

// record -> class 변경
@Data
public class HospitalGradeRegister {
    private String hospitalCode;
    private String asmGrd01;
    private String asmGrd02;
    private String asmGrd03;
    private String asmGrd04;
    private String asmGrd05;
    private String asmGrd06;
    private String asmGrd07;
    private String asmGrd08;
    private String asmGrd09;
    private String asmGrd10;
    private String asmGrd11;
    private String asmGrd12;
    private String asmGrd13;
    private String asmGrd14;
    private String asmGrd15;
    private String asmGrd16;
    private String asmGrd17;
    private String asmGrd18;
    private String asmGrd19;
    private String asmGrd20;
    private String asmGrd21;
    private String asmGrd22;
    private String asmGrd23;
    private String asmGrd24;

    public HospitalGradeRegister() {}

    public HospitalGradeRegister(
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
            String asmGrd24) {
        this.hospitalCode = hospitalCode;
        this.asmGrd01 = asmGrd01;
        this.asmGrd02 = asmGrd02;
        this.asmGrd03 = asmGrd03;
        this.asmGrd04 = asmGrd04;
        this.asmGrd05 = asmGrd05;
        this.asmGrd06 = asmGrd06;
        this.asmGrd07 = asmGrd07;
        this.asmGrd08 = asmGrd08;
        this.asmGrd09 = asmGrd09;
        this.asmGrd10 = asmGrd10;
        this.asmGrd11 = asmGrd11;
        this.asmGrd12 = asmGrd12;
        this.asmGrd13 = asmGrd13;
        this.asmGrd14 = asmGrd14;
        this.asmGrd15 = asmGrd15;
        this.asmGrd16 = asmGrd16;
        this.asmGrd17 = asmGrd17;
        this.asmGrd18 = asmGrd18;
        this.asmGrd19 = asmGrd19;
        this.asmGrd20 = asmGrd20;
        this.asmGrd21 = asmGrd21;
        this.asmGrd22 = asmGrd22;
        this.asmGrd23 = asmGrd23;
        this.asmGrd24 = asmGrd24;
    }

    // setHospital 메서드 (record 버전과 동일하게 불변객체 반환 패턴 유지)
    public HospitalGradeRegister setHospital(HospitalGradeRegister dto) {
        return new HospitalGradeRegister(
                dto.hospitalCode,
                dto.asmGrd01,
                dto.asmGrd02,
                dto.asmGrd03,
                dto.asmGrd04,
                dto.asmGrd05,
                dto.asmGrd06,
                dto.asmGrd07,
                dto.asmGrd08,
                dto.asmGrd09,
                dto.asmGrd10,
                dto.asmGrd11,
                dto.asmGrd12,
                dto.asmGrd13,
                dto.asmGrd14,
                dto.asmGrd15,
                dto.asmGrd16,
                dto.asmGrd17,
                dto.asmGrd18,
                dto.asmGrd19,
                dto.asmGrd20,
                dto.asmGrd21,
                dto.asmGrd22,
                dto.asmGrd23,
                dto.asmGrd24
        );
    }
}
