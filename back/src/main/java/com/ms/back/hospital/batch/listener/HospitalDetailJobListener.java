package com.ms.back.hospital.batch.listener;

import com.ms.back.hospital.batch.component.HospitalDetailCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HospitalDetailJobListener implements JobExecutionListener {

    private final HospitalDetailCache cache;

    @Override
    public void afterJob(JobExecution jobExecution) {
        cache.clear(); // 배치 끝나면 캐시 초기화
        log.info("✅ 병원 캐시 초기화 완료");
    }
}
