package com.ms.back.hospital.domain.service;

import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;
import com.ms.back.hospital.application.dto.HospitalInfoResponse.BaseInfo;
import com.ms.back.hospital.application.dto.HospitalListResponse;
import com.ms.back.hospital.application.port.HospitalDomainService;
import com.ms.back.hospital.batch.dto.HospitalDto;
import com.ms.back.hospital.domain.port.HospitalRepository;
import com.ms.back.hospital.exception.HospitalException.HospitalNotExist;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HospitalDomainServiceImpl implements HospitalDomainService {
    private final HospitalRepository hospitalRepository;

    private final static int R = 6371;

    @Override
    public List<Hospital> getAllHospitalData() {
        return hospitalRepository.getAllData();
    }

    @Override
    public BaseInfo getHospital(String hospitalCode) {
        Hospital hospital = findHospitalByCode(hospitalCode);

        return BaseInfo.builder()
                .hospitalCode(hospital.getHospitalCode())
                .name(hospital.getName())
                .address(hospital.getAddress())
                .callNumber(hospital.getCallNumber())
                .latitude(hospital.getLatitude())
                .longitude(hospital.getLongitude())
                .build();
    }

    @Override
    public List<HospitalListResponse> getHospitalsNearby(Double latitude, Double longitude, double distanceKm) {
        // DB에서 정수 기준으로 1차 필터링
        List<Hospital> candidates = hospitalRepository.findHospitalsByLatLngInt(latitude.intValue(), longitude.intValue());

        // 2차 필터링: 거리 계산 후 필터
        return candidates.stream()
                .filter(hospital -> {
                    if (hospital.getLatitude() == null || hospital.getLongitude() == null) return false;

                    double hospLat = Double.parseDouble(hospital.getLatitude());
                    double hospLng = Double.parseDouble(hospital.getLongitude());

                    double distance = calculateDistance(latitude, longitude, hospLat, hospLng);
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

    @Override
    public Page<HospitalDto> findHospitalsByKeyword(String name, String address, String callNumber, String categoryCode, Pageable pageable) {
        return hospitalRepository.searchByKeyword(name, address, callNumber, categoryCode, pageable).map(HospitalDto::from);
    }

    private Hospital findHospitalByCode(String hospitalCode) {
        return hospitalRepository.findByHospitalCode(hospitalCode)
                .orElseThrow(HospitalNotExist::new);
    }

    private double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
