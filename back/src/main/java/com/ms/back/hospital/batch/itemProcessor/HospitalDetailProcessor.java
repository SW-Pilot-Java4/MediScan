package com.ms.back.hospital.batch.itemProcessor;

import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;
import com.ms.back.hospital.Infrastructure.repository.entity.HospitalDetail;
import com.ms.back.hospital.batch.dto.HospitalDetailRegister;
import com.ms.back.hospital.domain.port.HospitalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static jakarta.xml.bind.DatatypeConverter.parseTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class HospitalDetailProcessor implements ItemProcessor<HospitalDetailRegister, HospitalDetail> {
    private final HospitalRepository hospitalRepository;

    @Override
    public HospitalDetail process(HospitalDetailRegister item) throws Exception {
        try {
//            문제 발생
//            item = item.formatTimeFields(
//                    normalizeTimeRange(item.lunchWeekday()), normalizeTimeRange(item.lunchSaturday()),
//                    normalizeTimeRange(item.receptionWeekday()), normalizeTimeRange(item.receptionSaturday())
//                    );
            return item.to();
        }catch (RuntimeException e) {
            // 존재하지 않는 Hospital Code가 있을 경우 해당 데이터는 Drop
            log.error("등록되지 않은 HospitalCode"+item.hospitalCode());
            return null;
        }

    }

    private static String normalizeTimeRange(String input) {
        if (input == null) return "";

        // 한글 표현 통일
        input = input.replaceAll("[오전\\s]", "")
                .replaceAll("오후", "PM")
                .replaceAll("~+", "~")
                .replaceAll("−", "-")
                .replaceAll("–", "-")
                .replaceAll("—", "-")
                .replaceAll("부터", "")
                .replaceAll("까지", "")
                .replaceAll("시\\s*반", ":30")
                .replaceAll("시", ":00")
                .replaceAll("분", "")
                .replaceAll("\\(.*?\\)", "") // 괄호 제거
                .trim();

        // 숫자 앞에 PM 또는 AM이 붙은 경우를 분리하여 인식 가능하게 함
        input = input.replaceAll("PM(\\d{1,2})", "$1PM");
        input = input.replaceAll("AM(\\d{1,2})", "$1AM");

        // Range 구분자 표준화
        input = input.replaceAll("\\s*-\\s*|\\s*~\\s*|\\s*~\\s*|\\s*~\\s*", "~");

        // 시간 범위 추출
        String[] parts = input.split("~");
        if (parts.length != 2) return "";

        String endRaw = parts[1].trim();
        String startRaw = parts[0].trim();

        try {
            String result = parseTime(startRaw) + " ~ " + parseTime(endRaw);
            // ✅ 뒤에 붙은 텍스트 제거 (예: "13:00 ~ 14:00 추가설명", "13:00 ~ 14:00※비고")
            result = result.replaceAll("(\\d{2}:\\d{2} ~ \\d{2}:\\d{2}).*", "$1");
            return result;
        } catch (Exception e) {
            return "";
        }
    }
}
