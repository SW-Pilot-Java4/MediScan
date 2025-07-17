package com.ms.back.hospital.domain.service;

import com.ms.back.hospital.Infrastructure.repository.entity.HospitalDetail;
import com.ms.back.hospital.application.dto.HospitalInfoResponse.DetailInfo;
import com.ms.back.hospital.application.port.HospitalDetailDomainService;
import com.ms.back.hospital.domain.port.HospitalDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HospitalDetailDomainServiceImpl implements HospitalDetailDomainService {
    private final HospitalDetailRepository hospitalDetailRepository;

    @Override
    public DetailInfo getHospitalDetail(String hospitalCode) {
        HospitalDetail detail = hospitalDetailRepository.findByHospitalCode(hospitalCode)
                .orElse(null);

        if (detail == null) return null;

        return DetailInfo.builder()
                .departmentCodes(detail.getDepartmentCodes())
                .closedSunday(detail.getClosedSunday())
                .closedHoliday(detail.getClosedHoliday())
                .emergencyDayYn(detail.getEmergencyDayYn())
                .emergencyDayPhone1(detail.getEmergencyDayPhone1())
                .emergencyDayPhone2(detail.getEmergencyDayPhone2())
                .emergencyNightYn(detail.getEmergencyNightYn())
                .emergencyNightPhone1(detail.getEmergencyNightPhone1())
                .emergencyNightPhone2(detail.getEmergencyNightPhone2())
                .lunchWeekday(detail.getLunchWeekday())
                .lunchSaturday(detail.getLunchSaturday())
                .receptionWeekday(detail.getReceptionWeekday())
                .receptionSaturday(detail.getReceptionSaturday())
                .treatSunStart(detail.getTreatSunStart())
                .treatSunEnd(detail.getTreatSunEnd())
                .treatMonStart(detail.getTreatMonStart())
                .treatMonEnd(detail.getTreatMonEnd())
                .treatTueStart(detail.getTreatTueStart())
                .treatTueEnd(detail.getTreatTueEnd())
                .treatWedStart(detail.getTreatWedStart())
                .treatWedEnd(detail.getTreatWedEnd())
                .treatThuStart(detail.getTreatThuStart())
                .treatThuEnd(detail.getTreatThuEnd())
                .treatFriStart(detail.getTreatFriStart())
                .treatFriEnd(detail.getTreatFriEnd())
                .treatSatStart(detail.getTreatSatStart())
                .treatSatEnd(detail.getTreatSatEnd())
                .build();
    }
}
