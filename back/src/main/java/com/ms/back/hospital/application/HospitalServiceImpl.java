package com.ms.back.hospital.application;

import com.ms.back.hospital.application.dto.HospitalInfoResponse;
import com.ms.back.hospital.application.dto.HospitalListResponse;
import com.ms.back.hospital.application.port.HospitalDetailDomainService;
import com.ms.back.hospital.application.port.HospitalDomainService;
import com.ms.back.hospital.application.port.HospitalGradeDomainService;
import com.ms.back.hospital.persentation.port.HospitalService;
import com.ms.back.hospital.policy.HospitalPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HospitalServiceImpl implements HospitalService {

    private final HospitalDomainService hospitalDomainService;
    private final HospitalDetailDomainService hospitalDetailDomainService;
    private final HospitalGradeDomainService hospitalGradeDomainService;
    private final HospitalPolicy hospitalPolicy;

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
        List<HospitalListResponse> allHospitals = getAllHospitalData();
        Double lat = Double.parseDouble(latitude);
        Double lng = Double.parseDouble(longitude);

        // 위도 경도 문자열을 double로 변환하고 거리 계산 후 필터링
        return allHospitals.stream()
                .filter(hospital -> {
                    String hospLatStr = hospital.getLatitude();
                    String hospLngStr = hospital.getLongitude();

                    if (hospital.getLatitude() == null || hospital.getLatitude().isBlank() || hospital.getLongitude() == null || hospital.getLongitude().isBlank()) {
                        System.out.println("⚠️ 위도/경도 비어있는 병원: " + hospital.getLatitude()+" / " +hospital.getLongitude());
                        return false;
                    }

                    try {
                        double hospitalLat = Double.parseDouble(hospLatStr);
                        double hospitalLng = Double.parseDouble(hospLngStr);
                        double distance = calculateDistance(lat, lng, hospitalLat, hospitalLng);
                        return distance <= distanceKm;
                    } catch (NumberFormatException e) {
                        System.out.println("❌ 숫자 파싱 실패 - 위도: " + hospLatStr + ", 경도: " + hospLngStr);
                        return false;
                    }
                })
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
