package com.ms.back.hospital.itemReader;

import com.ms.back.hospital.HospitalFieldSetMapper;
import com.ms.back.hospital.entity.Hospital;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HospitalReader {
    @Bean
    public ItemReader<? extends Hospital> readerByCSV() {
        return new FlatFileItemReaderBuilder<Hospital>()
                .name("hospitalReader")
                .resource(new ClassPathResource("/csv/hospital.csv"))
                .fieldSetMapper(new HospitalFieldSetMapper())
                .linesToSkip(1)
                .delimited().delimiter(",")
                .names(
                        "hospitalCode",        // 암호화요양기호
                        "name",                // 요양기관명
                        "categoryCode",        // 종별코드
                        "categoryName",        // 종별코드명 (❌ 사용 안 함)
                        "regionCode",          // 시도코드
                        "regionName",          // 시도코드명 (❌ 사용 안 함)
                        "districtCode",        // 시군구코드
                        "districtName",        // 시군구코드명 (❌ 사용 안 함)
                        "town",                // 읍면동
                        "postalCode",          // 우편번호
                        "address",             // 주소
                        "callNumber",          // 전화번호
                        "homepage",            // 병원홈페이지 (❌ 사용 안 함)
                        "openDate",            // 개설일자 (❌ 사용 안 함)
                        "doctorTotal",         // 총의사수 (❌ 사용 안 함)
                        "doctorGeneral",       // 의과일반의 인원수 (❌ 사용 안 함)
                        "doctorIntern",        // 의과인턴 인원수 (❌ 사용 안 함)
                        "doctorResident",      // 의과레지던트 인원수 (❌ 사용 안 함)
                        "doctorSpecialist",    // 의과전문의 인원수 (❌ 사용 안 함)
                        "dentistGeneral",      // 치과일반의 인원수 (❌ 사용 안 함)
                        "dentistIntern",       // 치과인턴 인원수 (❌ 사용 안 함)
                        "dentistResident",     // 치과레지던트 인원수 (❌ 사용 안 함)
                        "dentistSpecialist",   // 치과전문의 인원수 (❌ 사용 안 함)
                        "koreanDoctorGeneral", // 한방일반의 인원수 (❌ 사용 안 함)
                        "koreanDoctorIntern",  // 한방인턴 인원수 (❌ 사용 안 함)
                        "koreanDoctorResident",// 한방레지던트 인원수 (❌ 사용 안 함)
                        "koreanDoctorSpecialist", // 한방전문의 인원수 (❌ 사용 안 함)
                        "midwife",             // 조산사 인원수 (❌ 사용 안 함)
                        "longitude",           // 좌표 X
                        "latitude"             // 좌표 Y
                )
                .build();
    }
}
