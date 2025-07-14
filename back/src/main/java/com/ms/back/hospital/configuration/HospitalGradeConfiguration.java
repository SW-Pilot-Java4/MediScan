package com.ms.back.hospital.configuration;

import com.ms.back.hospital.entity.Hospital;
import com.ms.back.hospital.entity.HospitalDetail;
import com.ms.back.hospital.entity.HospitalGrade;
import com.ms.back.hospital.itemProcessor.HospitalDetailProcessor;
import com.ms.back.hospital.itemProcessor.HospitalGradeProcessor;
import com.ms.back.hospital.itemProcessor.HospitalProcessor;
import com.ms.back.hospital.itemReader.HospitalDetailReader;
import com.ms.back.hospital.itemReader.HospitalGradeReader;
import com.ms.back.hospital.itemReader.HospitalReader;
import com.ms.back.hospital.itemWriter.HospitalDetailWriter;
import com.ms.back.hospital.itemWriter.HospitalGradeWriter;
import com.ms.back.hospital.itemWriter.HospitalWriter;
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

//    Reader
    private final HospitalGradeReader hospitalGradeReader;
    private final HospitalReader hospitalReader;
    private final HospitalDetailReader hospitalDetailReader;

//    Processor
    private final HospitalGradeProcessor hospitalGradeProcessor;
    private final HospitalProcessor hospitalProcessor;
    private final HospitalDetailProcessor hospitalDetailProcessor;

//    Writer
    private final HospitalGradeWriter hospitalGradeWriter;
    private final HospitalWriter hospitalWriter;
    private final HospitalDetailWriter hospitalDetailWriter;

    @Bean(name ="loadHospitalJob" )
    public Job loadHospitalJob() {
        return new JobBuilder("loadHospitalJob", jobRepository)
                .start(loadHospitalStep())
                .listener(jobListener)
                .build();

    }

    @JobScope
    @Bean
    public Step loadHospitalStep() {
        return new StepBuilder("loadHospitalStep", jobRepository)
                .<Hospital, Hospital>chunk(1000,ptm)
                .reader(hospitalReader.readerByCSV())
                .processor(hospitalProcessor)
                .writer(hospitalWriter)
                .build();
    }

    @Bean(name ="loadHospitalGradeJob" )
    public Job loadHospitalGradeJob() {
        return new JobBuilder("loadHospitalGradeJob", jobRepository)
                .start(loadHospitalGradeStep())
                .listener(jobListener)
                .build();

    }

    @JobScope
    @Bean
    public Step loadHospitalGradeStep() {
        return new StepBuilder("loadHospitalGradeStep", jobRepository)
                .<HospitalGrade, HospitalGrade>chunk(10,ptm)
                .reader(hospitalGradeReader.read())
                .processor(hospitalGradeProcessor)
                .writer(hospitalGradeWriter)
                .build();
    }

    @Bean(name ="loadHospitalDetailJob" )
    public Job loadHospitalDetailJob() {
        return new JobBuilder("loadHospitalDetailJob", jobRepository)
                .start(loadHospitalDetailStep())
                .listener(jobListener)
                .build();

    }

    @JobScope
    @Bean
    public Step loadHospitalDetailStep() {
        return new StepBuilder("loadHospitalDetailStep", jobRepository)
                .<HospitalDetail, HospitalDetail>chunk(1000,ptm)
                .reader(hospitalDetailReader.readerByHospitalDetail())
                .processor(hospitalDetailProcessor)
                .writer(hospitalDetailWriter)
                .build();
    }

}