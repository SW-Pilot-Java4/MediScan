package com.ms.back.hospital.application.dto;


import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record HospitalInfoResponse(
        BaseInfo baseInfo,
        DetailInfo detailInfo,
        GradeInfo gradeInfo
) {
    @Builder
    public record BaseInfo(
            String hospitalCode,
            String name,
            String address,
            String callNumber,
            String latitude,
            String longitude
    ) {
    }

    @Builder
    public record DetailInfo(
            List<String> departmentCodes,
            String closedSunday, String closedHoliday,
            String emergencyDayYn,
            String emergencyDayPhone1, String emergencyDayPhone2,
            String emergencyNightYn,
            String emergencyNightPhone1, String emergencyNightPhone2,
            String lunchWeekday, String lunchSaturday,
            String receptionWeekday, String receptionSaturday,
            String treatSunStart, String treatSunEnd,
            String treatMonStart, String treatMonEnd,
            String treatTueStart, String treatTueEnd,
            String treatWedStart, String treatWedEnd,
            String treatThuStart, String treatThuEnd,
            String treatFriStart, String treatFriEnd,
            String treatSatStart, String treatSatEnd
    ) {
    }

    @Builder
    public record GradeInfo(
            Map<String, Long> grades
    ) {}
}
