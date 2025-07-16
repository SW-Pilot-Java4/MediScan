package com.ms.back.hospital.batch.itemReader;

import com.ms.back.hospital.batch.dto.HospitalDetailRegister;
import com.ms.back.hospital.batch.fieldSetMapper.HospitalDetailFieldSetMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HospitalDetailReader {

    @Bean
    public ItemReader<? extends HospitalDetailRegister> readerByHospitalDetail() {
        return new FlatFileItemReaderBuilder<HospitalDetailRegister>()
                .name("hospitalDetailReader")
                .resource(new ClassPathResource("/csv/hospital_detail.csv"))
                .fieldSetMapper(new HospitalDetailFieldSetMapper())
                .linesToSkip(1)
                .delimited()
                .delimiter(",")
                .names(
                        "hospitalCode",          // 암호화요양기호
                        "name",                  // 요양기관명
                        "locationBuildingName",  // 위치_공공건물(장소)명
                        "locationDirection",     // 위치_방향
                        "locationDistance",      // 위치_거리
                        "parkingCapacity",       // 주차_가능대수
                        "parkingFeeYn",          // 주차_비용 부담여부
                        "parkingNote",           // 주차_기타 안내사항
                        "closedSunday",          // 휴진안내_일요일
                        "closedHoliday",         // 휴진안내_공휴일
                        "emergencyDayYn",        // 응급실_주간_운영여부
                        "emergencyDayPhone1",    // 응급실_주간_전화번호1
                        "emergencyDayPhone2",    // 응급실_주간_전화번호2
                        "emergencyNightYn",      // 응급실_야간_운영여부
                        "emergencyNightPhone1",  // 응급실_야간_전화번호1
                        "emergencyNightPhone2",  // 응급실_야간_전화번호2
                        "lunchWeekday",          // 점심시간_평일
                        "lunchSaturday",         // 점심시간_토요일
                        "receptionWeekday",      // 접수시간_평일
                        "receptionSaturday",     // 접수시간_토요일
                        "treatSunStart",         // 진료시작시간_일요일
                        "treatSunEnd",           // 진료종료시간_일요일
                        "treatMonStart",         // 진료시작시간_월요일
                        "treatMonEnd",           // 진료종료시간_월요일
                        "treatTueStart",         // 진료시작시간_화요일
                        "treatTueEnd",           // 진료종료시간_화요일
                        "treatWedStart",         // 진료시작시간_수요일
                        "treatWedEnd",           // 진료종료시간_수요일
                        "treatThuStart",         // 진료시작시간_목요일
                        "treatThuEnd",           // 진료종료시간_목요일
                        "treatFriStart",         // 진료시작시간_금요일
                        "treatFriEnd",           // 진료종료시간_금요일
                        "treatSatStart",         // 진료시작시간_토요일
                        "treatSatEnd"            // 진료종료시간_토요일
                )
                .build();
    }
}
