package com.ms.back.hospital.batch.dto;

import java.util.List;

public class HospitalCodeWithDepartments {
    private final String hospitalCode;
    private final String hospitalName;
    private final List<String> departmentCodes;

    public HospitalCodeWithDepartments(String hospitalCode, String hospitalName, List<String> departmentCodes) {
        this.hospitalCode = hospitalCode;
        this.hospitalName = hospitalName;
        this.departmentCodes = departmentCodes;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public List<String> getDepartmentCodes() {
        return departmentCodes;
    }
}