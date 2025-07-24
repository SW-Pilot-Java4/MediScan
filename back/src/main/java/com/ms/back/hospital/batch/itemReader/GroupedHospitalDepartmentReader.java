package com.ms.back.hospital.batch.itemReader;

import com.ms.back.hospital.batch.fieldSetMapper.HospitalDepartmentFieldSetMapper;
import com.ms.back.hospital.batch.dto.HospitalCodeWithDepartments;
import com.ms.back.hospital.batch.dto.HospitalDepartmentRow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class GroupedHospitalDepartmentReader implements ItemReader<HospitalCodeWithDepartments> {

    @Bean
    public ItemReader<? extends HospitalCodeWithDepartments> readerByHospitalDepartment() {
        return new GroupedHospitalDepartmentReader();
    }

    private final FlatFileItemReader<HospitalDepartmentRow> delegate;
    private HospitalDepartmentRow nextRow;

    public GroupedHospitalDepartmentReader() {
        this.delegate = new FlatFileItemReaderBuilder<HospitalDepartmentRow>()
                .name("groupedHospitalDepartmentReader")
                .resource(new ClassPathResource("/csv/hospital_department.csv"))
//                .linesToSkip(355507) // 1
                .linesToSkip(1) // 너무 많은 데이터를 읽고 있어서 개발 기간 동안 제한
                .delimited().delimiter(",")
                .names("hospitalCode", "hospitalName", "departmentCode", "departmentName", "specialistCount", "optionalDoctorCount")
                .fieldSetMapper(new HospitalDepartmentFieldSetMapper())
                .build();
        this.delegate.open(new ExecutionContext());
    }

    @Override
    public HospitalCodeWithDepartments read() throws Exception {
        if (nextRow == null) {
            nextRow = delegate.read();
            if (nextRow == null) return null;
        }

        String currentCode = nextRow.getHospitalCode();
        List<String> deptList = new ArrayList<>();
        deptList.add(nextRow.getDepartmentCode());

        while (true) {
            HospitalDepartmentRow row = delegate.read();
            if (row == null) {
                nextRow = null;
                break;
            }

            if (!row.getHospitalCode().equals(currentCode)) {
                nextRow = row;
                break;
            }

            deptList.add(row.getDepartmentCode());
        }

        return new HospitalCodeWithDepartments(currentCode, deptList);
    }
}