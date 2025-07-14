package com.ms.back.hospital.util;

import com.ms.back.global.exception.MediscanCustomException;
import com.ms.back.hospital.entity.Hospital;
import com.ms.back.hospital.repository.HospitalRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoadData {
    private final HospitalRepositoryImpl hospitalRepositoryImpl;
    private final Policy policy;
// 임시 서비스
    @Transactional
    public void readSampleData()  {
        ClassPathResource resource = new ClassPathResource("csv/hospital.csv");

        try (InputStream inputStream = resource.getInputStream();
             Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {

                String ykiho = record.get(0);
                String name = record.get(1);
                String categoryCode = record.get(2);
                String regionCode = record.get(4);
                String districtCode = record.get(6);
                String postalCode = record.get(9);
                String address = record.get(10);
                String callNumber = record.get(11);
                String x = record.get(28);
                String y = record.get(29);

                Hospital hospital = Hospital.create(ykiho, name, categoryCode, regionCode
                        ,districtCode, postalCode, address, callNumber, x, y);

                hospitalRepositoryImpl.save(hospital);
            }
        }catch (FileNotFoundException e) {
            throw new MediscanCustomException.NotFoundFileException();
        }catch (IllegalArgumentException e) {
            throw new MediscanCustomException.InvalidFileFormatException();
        }catch (IOException e) {
            throw new MediscanCustomException.FileReadingErrorException();
        }
    }

    public void exceptionTest() {
        policy.exceptionTest(true);
    }
}
