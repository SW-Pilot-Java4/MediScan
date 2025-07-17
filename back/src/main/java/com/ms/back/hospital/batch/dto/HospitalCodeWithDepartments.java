package com.ms.back.hospital.batch.dto;

import java.util.List;

public class HospitalCodeWithDepartments {
    private final String hospitalCode;
    private final List<String> departmentCodes;

    public HospitalCodeWithDepartments(String hospitalCode, List<String> departmentCodes) {
        this.hospitalCode = hospitalCode;
        this.departmentCodes = departmentCodes;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public List<String> getDepartmentCodes() {
        return departmentCodes;
    }
}