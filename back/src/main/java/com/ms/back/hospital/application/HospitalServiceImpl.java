package com.ms.back.hospital.application;

import com.ms.back.hospital.Infrastructure.repository.HospitalJPARepository;
import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;
import com.ms.back.hospital.application.dto.HospitalInfoResponse;
import com.ms.back.hospital.application.dto.HospitalListResponse;
import com.ms.back.hospital.application.port.HospitalDetailDomainService;
import com.ms.back.hospital.application.port.HospitalDomainService;
import com.ms.back.hospital.application.port.HospitalGradeDomainService;
import com.ms.back.hospital.persentation.port.HospitalService;
import com.ms.back.hospital.policy.HospitalPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.*;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HospitalServiceImpl implements HospitalService {

    private final HospitalDomainService hospitalDomainService;
    private final HospitalDetailDomainService hospitalDetailDomainService;
    private final HospitalGradeDomainService hospitalGradeDomainService;
    private final HospitalPolicy hospitalPolicy;
    private final HospitalJPARepository hospitalRepository;

    @Override
    public List<HospitalListResponse> getAllHospitalData() {
        return hospitalDomainService.getAllHospitalData().stream()
                .map(HospitalListResponse::from)
                .toList();
    }

    @Override
    public HospitalInfoResponse assembleHospitalInfo(String hospitalCode) {
        HospitalInfoResponse.BaseInfo base = hospitalDomainService.getHospital(hospitalCode);
        HospitalInfoResponse.DetailInfo detail = hospitalDetailDomainService.getHospitalDetail(hospitalCode);
        HospitalInfoResponse.GradeInfo grade = hospitalGradeDomainService.getHospitalGrade(hospitalCode);

        return HospitalInfoResponse.builder()
                .baseInfo(base)
                .detailInfo(detail)
                .gradeInfo(grade)
                .build();
    }

    @Override
    public List<HospitalListResponse> getHospitalsNearby(String latitude, String longitude, double distanceKm) {
        double lat = Double.parseDouble(latitude);
        double lng = Double.parseDouble(longitude);

        int latInt = (int) lat;
        int lngInt = (int) lng;

        // DB에서 정수 기준으로 1차 필터링
        List<Hospital> candidates = hospitalRepository.findHospitalsByLatLngInt(latInt, lngInt);

        // 2차 필터링: 거리 계산 후 필터
        return candidates.stream()
                .filter(hospital -> {
                    if (hospital.getLatitude() == null || hospital.getLongitude() == null) return false;

                    double hospLat = Double.parseDouble(hospital.getLatitude());
                    double hospLng = Double.parseDouble(hospital.getLongitude());

                    double distance = calculateDistance(lat, lng, hospLat, hospLng);
                    return distance <= distanceKm;
                })
                .map(hospital -> new HospitalListResponse(
                        hospital.getHospitalCode(),
                        hospital.getName(),
                        hospital.getAddress(),
                        String.valueOf(hospital.getLatitude()),
                        String.valueOf(hospital.getLongitude())
                        // 필요 필드 추가
                ))
                .toList();
    }

    // 두 좌표 사이 거리 계산 (킬로미터 단위, 하버사인 공식 사용)
    private double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        final int R = 6371; // 지구 반경 km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
