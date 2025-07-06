package com.ms.back.hospital.util;

import com.ms.back.hospital.entity.Hospital;
import com.ms.back.hospital.repository.HospitalRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoadData {
    private final HospitalRepository hospitalRepository;
// 임시 서비스
    @Transactional
    public void readSampleData() throws Exception {
        ClassPathResource resource = new ClassPathResource("initData/sampleData.csv");

        try (InputStream inputStream = resource.getInputStream();
             Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {

                String ykiho = record.get(0);
                String name = record.get(1);
                String code = record.get(2);
                String address = record.get(10);
                String callNumber = record.get(11);
                String x = record.get(28);
                String y = record.get(29);

                Hospital hospital = Hospital.create(ykiho, name, code, address, callNumber, x, y);

                hospitalRepository.save(hospital);
            }
        }
    }
}
