package com.ms.back.hospital.batch.dto;

import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;
import com.ms.back.hospital.Infrastructure.repository.dao.HospitalDAO;

import java.util.List;

public record HospitalDetailRegister(
        String hospitalCode,
        Hospital hospital,
        List<String> departmentCodes,
        String closedSunday,
        String closedHoliday,
        String emergencyDayYn,
        String emergencyDayPhone1,
        String emergencyDayPhone2,
        String emergencyNightYn,
        String emergencyNightPhone1,
        String emergencyNightPhone2,
        String lunchWeekday,
        String lunchSaturday,
        String receptionWeekday,
        String receptionSaturday,
        String treatSunStart,
        String treatSunEnd,
        String treatMonStart,
        String treatMonEnd,
        String treatTueStart,
        String treatTueEnd,
        String treatWedStart,
        String treatWedEnd,
        String treatThuStart,
        String treatThuEnd,
        String treatFriStart,
        String treatFriEnd,
        String treatSatStart,
        String treatSatEnd
) {
    public HospitalDetailRegister setHospital(HospitalDetailRegister dto, HospitalDAO hospitalDAO) {
        return new HospitalDetailRegister(
                dto.hospitalCode,
                hospital,
                dto.departmentCodes,
                dto.closedSunday,
                dto.closedHoliday,
                dto.emergencyDayYn,
                dto.emergencyDayPhone1,
                dto.emergencyDayPhone2,
                dto.emergencyNightYn,
                dto.emergencyNightPhone1,
                dto.emergencyNightPhone2,
                dto.lunchWeekday,
                dto.lunchSaturday,
                dto.receptionWeekday,
                dto.receptionSaturday,
                dto.treatSunStart,
                dto.treatSunEnd,
                dto.treatMonStart,
                dto.treatMonEnd,
                dto.treatTueStart,
                dto.treatTueEnd,
                dto.treatWedStart,
                dto.treatWedEnd,
                dto.treatThuStart,
                dto.treatThuEnd,
                dto.treatFriStart,
                dto.treatFriEnd,
                dto.treatSatStart,
                dto.treatSatEnd
        );
    }
}
