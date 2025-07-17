package com.ms.back.hospital.Infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "hospital_grade")
public class HospitalGrade {
    @Id
    private String hospitalCode;

//    @OneToOne(fetch = FetchType.LAZY)
//    @MapsId
//    @JoinColumn(name = "hospital_code")
//    private Hospital hospital;

    @Column(name = "asm_grd01")
    private String asmGrd01;

    @Column(name = "asm_grd02")
    private String asmGrd02;

    @Column(name = "asm_grd03")
    private String asmGrd03;

    @Column(name = "asm_grd04")
    private String asmGrd04;

    @Column(name = "asm_grd05")
    private String asmGrd05;

    @Column(name = "asm_grd06")
    private String asmGrd06;

    @Column(name = "asm_grd07")
    private String asmGrd07;

    @Column(name = "asm_grd08")
    private String asmGrd08;

    @Column(name = "asm_grd09")
    private String asmGrd09;

    @Column(name = "asm_grd10")
    private String asmGrd10;

    @Column(name = "asm_grd11")
    private String asmGrd11;

    @Column(name = "asm_grd12")
    private String asmGrd12;

    @Column(name = "asm_grd13")
    private String asmGrd13;

    @Column(name = "asm_grd14")
    private String asmGrd14;

    @Column(name = "asm_grd15")
    private String asmGrd15;

    @Column(name = "asm_grd16")
    private String asmGrd16;

    @Column(name = "asm_grd17")
    private String asmGrd17;

    @Column(name = "asm_grd18")
    private String asmGrd18;

    @Column(name = "asm_grd19")
    private String asmGrd19;

    @Column(name = "asm_grd20")
    private String asmGrd20;

    @Column(name = "asm_grd21")
    private String asmGrd21;

    @Column(name = "asm_grd22")
    private String asmGrd22;

    @Column(name = "asm_grd23")
    private String asmGrd23;

    @Column(name = "asm_grd24")
    private String asmGrd24;

    private HospitalGrade(
            String hospitalCode,
            String asmGrd01, String asmGrd02, String asmGrd03, String asmGrd04,
            String asmGrd05, String asmGrd06, String asmGrd07, String asmGrd08,
            String asmGrd09, String asmGrd10, String asmGrd11, String asmGrd12,
            String asmGrd13, String asmGrd14, String asmGrd15, String asmGrd16,
            String asmGrd17, String asmGrd18, String asmGrd19, String asmGrd20,
            String asmGrd21, String asmGrd22, String asmGrd23, String asmGrd24
    ) {
        this.hospitalCode = hospitalCode;
        this.asmGrd01 = asmGrd01;this.asmGrd02 = asmGrd02;this.asmGrd03 = asmGrd03;this.asmGrd04 = asmGrd04;this.asmGrd05 = asmGrd05;
        this.asmGrd06 = asmGrd06;this.asmGrd07 = asmGrd07;this.asmGrd08 = asmGrd08;this.asmGrd09 = asmGrd09;this.asmGrd10 = asmGrd10;
        this.asmGrd11 = asmGrd11;this.asmGrd12 = asmGrd12;this.asmGrd13 = asmGrd13;this.asmGrd14 = asmGrd14;this.asmGrd15 = asmGrd15;
        this.asmGrd16 = asmGrd16;this.asmGrd17 = asmGrd17;this.asmGrd18 = asmGrd18;this.asmGrd19 = asmGrd19;this.asmGrd20 = asmGrd20;
        this.asmGrd21 = asmGrd21;this.asmGrd22 = asmGrd22;this.asmGrd23 = asmGrd23;this.asmGrd24 = asmGrd24;
    }

    public HospitalGrade() {

    }

    public static HospitalGrade create(
            String hospitalCode,
            String asmGrd01, String asmGrd02, String asmGrd03, String asmGrd04,
            String asmGrd05, String asmGrd06, String asmGrd07, String asmGrd08,
            String asmGrd09, String asmGrd10, String asmGrd11, String asmGrd12,
            String asmGrd13, String asmGrd14, String asmGrd15, String asmGrd16,
            String asmGrd17, String asmGrd18, String asmGrd19, String asmGrd20,
            String asmGrd21, String asmGrd22, String asmGrd23, String asmGrd24
    ) {
        return new HospitalGrade(
                hospitalCode,
                asmGrd01, asmGrd02, asmGrd03, asmGrd04, asmGrd05,
                asmGrd06, asmGrd07, asmGrd08, asmGrd09, asmGrd10,
                asmGrd11, asmGrd12, asmGrd13, asmGrd14, asmGrd15,
                asmGrd16, asmGrd17, asmGrd18, asmGrd19, asmGrd20,
                asmGrd21, asmGrd22, asmGrd23, asmGrd24
        );
    }
}