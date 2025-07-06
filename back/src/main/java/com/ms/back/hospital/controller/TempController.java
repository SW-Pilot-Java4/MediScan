package com.ms.back.hospital.controller;

import com.ms.back.hospital.util.LoadData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController("")
@RequiredArgsConstructor
public class TempController {
    private final LoadData loadData;

    @PostMapping("/init")
    public void init() {
        try {
            loadData.readSampleData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
