package com.ms.back.hospital.controller;

import com.ms.back.hospital.entity.Hospital;
import com.ms.back.hospital.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hospitals")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // React 개발 서버 주소
public class HospitalController {

    private final HospitalRepository hospitalRepository;

    @GetMapping
    public List<Hospital> getHospitals(@RequestParam(defaultValue = "10") int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return hospitalRepository.findAll(pageable).getContent();
    }

}
