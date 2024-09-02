package com.bit.springboard.config;

import com.bit.springboard.job.InactiveMemberTasklet;
import com.bit.springboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
// 3
public class MemberJobConfiguration {
    private final JobRepository jobRepository;
    private final MemberRepository memberRepository;
    private final PlatformTransactionManager platformTransactionManager;

    // Spring Boot 어플리케이션 실행 시 Job의 execute메소드가 실행되는데
    // JobBuilder에 의해 생성되는 SimpleJob 객체의 doExecute 메소드가 실행
    @Bean
    public Job memberJob(){
        return new JobBuilder("inactiveMemberJob", jobRepository)
                .start(inactiveMemberStep())
                .build();
    }

    @Bean
    public Step inactiveMemberStep(){
        return new StepBuilder("inactiveMemberStep", jobRepository)
                .tasklet(new InactiveMemberTasklet(memberRepository), platformTransactionManager)
                .build();
    }
}
