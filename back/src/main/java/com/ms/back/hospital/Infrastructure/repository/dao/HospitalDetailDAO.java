package com.ms.back.hospital.Infrastructure.repository.dao;

import com.ms.back.hospital.batch.dto.HospitalDetailRegister;
import com.ms.back.hospital.Infrastructure.repository.entity.HospitalDetail;

import java.util.List;

public record HospitalDetailDAO(
        Long id,
        String hospitalCode,
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
    public HospitalDetail to() {
        return HospitalDetail.create(
                id,
                hospitalCode,
                departmentCodes,
                closedSunday,
                closedHoliday,
                emergencyDayYn,
                emergencyDayPhone1,
                emergencyDayPhone2,
                emergencyNightYn,
                emergencyNightPhone1,
                emergencyNightPhone2,
                lunchWeekday,
                lunchSaturday,
                receptionWeekday,
                receptionSaturday,
                treatSunStart,
                treatSunEnd,
                treatMonStart,
                treatMonEnd,
                treatTueStart,
                treatTueEnd,
                treatWedStart,
                treatWedEnd,
                treatThuStart,
                treatThuEnd,
                treatFriStart,
                treatFriEnd,
                treatSatStart,
                treatSatEnd
        );
    }

    public static HospitalDetailDAO from(HospitalDetailRegister dto) {
        return new HospitalDetailDAO(
                null,
                dto.hospitalCode(),
                dto.departmentCodes(),
                dto.closedSunday(),
                dto.closedHoliday(),
                dto.emergencyDayYn(),
                dto.emergencyDayPhone1(),
                dto.emergencyDayPhone2(),
                dto.emergencyNightYn(),
                dto.emergencyNightPhone1(),
                dto.emergencyNightPhone2(),
                dto.lunchWeekday(),
                dto.lunchSaturday(),
                dto.receptionWeekday(),
                dto.receptionSaturday(),
                dto.treatSunStart(),
                dto.treatSunEnd(),
                dto.treatMonStart(),
                dto.treatMonEnd(),
                dto.treatTueStart(),
                dto.treatTueEnd(),
                dto.treatWedStart(),
                dto.treatWedEnd(),
                dto.treatThuStart(),
                dto.treatThuEnd(),
                dto.treatFriStart(),
                dto.treatFriEnd(),
                dto.treatSatStart(),
                dto.treatSatEnd()
        );
    }

    public static HospitalDetailDAO from(HospitalDetail entity) {
        return new HospitalDetailDAO(
                entity.getId(),
                entity.getHospitalCode(),
                entity.getDepartmentCodes(),
                entity.getClosedSunday(),
                entity.getClosedHoliday(),
                entity.getEmergencyDayYn(),
                entity.getEmergencyDayPhone1(),
                entity.getEmergencyDayPhone2(),
                entity.getEmergencyNightYn(),
                entity.getEmergencyNightPhone1(),
                entity.getEmergencyNightPhone2(),
                entity.getLunchWeekday(),
                entity.getLunchSaturday(),
                entity.getReceptionWeekday(),
                entity.getReceptionSaturday(),
                entity.getTreatSunStart(),
                entity.getTreatSunEnd(),
                entity.getTreatMonStart(),
                entity.getTreatMonEnd(),
                entity.getTreatTueStart(),
                entity.getTreatTueEnd(),
                entity.getTreatWedStart(),
                entity.getTreatWedEnd(),
                entity.getTreatThuStart(),
                entity.getTreatThuEnd(),
                entity.getTreatFriStart(),
                entity.getTreatFriEnd(),
                entity.getTreatSatStart(),
                entity.getTreatSatEnd()
        );
    }

    public HospitalDetailDAO setDepartmentCodes(HospitalDetailDAO dao, List<String> departmentCodes) {
        return new HospitalDetailDAO(
                dao.id,
                dao.hospitalCode,
                departmentCodes,
                dao.closedSunday,
                dao.closedHoliday,
                dao.emergencyDayYn,
                dao.emergencyDayPhone1,
                dao.emergencyDayPhone2,
                dao.emergencyNightYn,
                dao.emergencyNightPhone1,
                dao.emergencyNightPhone2,
                dao.lunchWeekday,
                dao.lunchSaturday,
                dao.receptionWeekday,
                dao.receptionSaturday,
                dao.treatSunStart,
                dao.treatSunEnd,
                dao.treatMonStart,
                dao.treatMonEnd,
                dao.treatTueStart,
                dao.treatTueEnd,
                dao.treatWedStart,
                dao.treatWedEnd,
                dao.treatThuStart,
                dao.treatThuEnd,
                dao.treatFriStart,
                dao.treatFriEnd,
                dao.treatSatStart,
                dao.treatSatEnd
        );
    }
}
