package com.ms.back.hospital.Runner;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class BatchRunner {
    private final JobLauncher jobLauncher;
    private final Job loadHospitalJob;
    private final Job loadHospitalDetailJob;

    @Bean
    public ApplicationRunner runHospitalJobOnStartup() {
        return args -> {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("startTime", LocalDateTime.now().toString())
                    .toJobParameters();

            jobLauncher.run(loadHospitalJob, jobParameters);
            jobLauncher.run(loadHospitalDetailJob, jobParameters);
        };
    }
}
