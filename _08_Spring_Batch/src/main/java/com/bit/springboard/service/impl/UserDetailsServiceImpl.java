package com.bit.springboard.service.impl;

import com.bit.springboard.entity.CustomUserDetails;
import com.bit.springboard.entity.Member;
import com.bit.springboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    // Spring Security 인증 과정에서 자동으로 호출되는 메소드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findByUsername(username);

        if(!member.get().isActive()){
            throw new RuntimeException("inactive");
        }

        // 로그인 성공하면 마지막 로그인 날짜 변경
        member.get().setLastLoginDate(LocalDateTime.now());
        // 변경사항 저장
        memberRepository.save(member.get());
        // commit
        memberRepository.flush();

        return CustomUserDetails.builder()
                .member(member.orElseThrow(() -> new RuntimeException("member not exist")))
                .build();
    }
}
