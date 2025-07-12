package com.ms.back.hospital.configuration;

import com.ms.back.hospital.entity.HospitalGrade;
import com.ms.back.hospital.itemProcessor.HospitalGradeProcessor;
import com.ms.back.hospital.itemReader.HospitalGradeReader;
import com.ms.back.hospital.itemWriter.HospitalGradeWriter;
import com.ms.back.hospital.listener.JobListener;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class HospitalGradeConfiguration {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager ptm;
    private final JobListener jobListener;
    private final HospitalGradeReader hospitalGradeReader;
    private final HospitalGradeProcessor hospitalGradeProcessor;
    private final HospitalGradeWriter hospitalGradeWriter;

    @Bean(name ="loadHospitalGradeJob" )
    public Job loadHospitalGradeJob() {
        return new JobBuilder("loadHospitalGradeJob", jobRepository)
                .start(loadHospitalGradeJobStep1())
                .listener(jobListener)
                .build();

    }

    @JobScope
    @Bean
    public Step loadHospitalGradeJobStep1() {
        return new StepBuilder("loadHospitalGradeJobStep1", jobRepository)
                .<HospitalGrade, HospitalGrade>chunk(10,ptm)
                .reader(hospitalGradeReader.read())
                .processor(hospitalGradeProcessor)
                .writer(hospitalGradeWriter)
                .build();
    }


}