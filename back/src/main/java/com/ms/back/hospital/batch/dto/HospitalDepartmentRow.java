package com.ms.back.hospital.batch.dto;

public class HospitalDepartmentRow {
    private String hospitalCode;
    private String departmentCode;

    // constructor, getter
    public HospitalDepartmentRow(String hospitalCode, String departmentCode) {
        this.hospitalCode = hospitalCode;
        this.departmentCode = departmentCode;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }
}