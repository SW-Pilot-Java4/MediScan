package com.ms.back.hospital.util;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoadData {
// 임시 서비스
    @Transactional
    public void readSampleData() throws Exception {
        ClassPathResource resource = new ClassPathResource("initData/sampleData.csv");

        try (InputStream inputStream = resource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",", -1); // 빈 문자열도 포함해서 split

                if (tokens.length >= 32) {
                    String ykiho = tokens[0];
                    String name = tokens[1];
                    String classCode = tokens[2];
                    String address = tokens[10];
                    String phone = tokens[11];
                    String x = tokens[30];
                    String y = tokens[31];

                    System.out.println("통과 행 형식: " + line);
                } else {
                    System.err.println("⚠️ 잘못된 행 형식: " + line);
                }
            }
        }
    }
}
