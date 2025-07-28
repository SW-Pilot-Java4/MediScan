package com.ms.back.hospital.batch.component;

import com.ms.back.hospital.Infrastructure.repository.entity.HospitalDetail;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class HospitalDetailCache {
    private final Map<String, HospitalDetail> detailMap = new ConcurrentHashMap<>();

    public void put(HospitalDetail detail) {
        detailMap.put(detail.getHospitalCode(), detail);
    }

    public Optional<HospitalDetail> get(String hospitalCode) {
        return Optional.ofNullable(detailMap.get(hospitalCode));
    }

    public Collection<HospitalDetail> getAll() {
        return detailMap.values();
    }

    public void clear() {
        detailMap.clear();
    }
}