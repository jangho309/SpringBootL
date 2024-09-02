package com.bit.springboard.job;

import com.bit.springboard.entity.Member;
import com.bit.springboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
// 4
public class InactiveMemberTasklet implements Tasklet {
    private final MemberRepository memberRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // 오늘로부터 1년 전 날짜
        LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1);

        // 로그인 날짜가 1년 이전인 Member 목록 조회
        List<Member> inactiveMemberList = memberRepository.findInactiveMembers(oneYearAgo);

        // 로그인 날짜가 1년 이전인 목록의 Member들의 active를 false로 변경
        inactiveMemberList.forEach(member -> {
            log.info("InActive Member username: {}", member.getUsername());

            member.setActive(false);

            memberRepository.save(member);
        });

        return RepeatStatus.FINISHED;
    }
}
