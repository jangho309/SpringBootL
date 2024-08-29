package com.bit.springboard.service.impl;

import com.bit.springboard.dto.MemberDto;
import com.bit.springboard.entity.Member;
import com.bit.springboard.mapper.MemberMapper;
import com.bit.springboard.repository.MemberRepository;
import com.bit.springboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberDto save(MemberDto memberDto) {
        memberMapper.save(memberDto);
        return memberMapper.findLastMember();
    }

    @Override
    public List<MemberDto> findAll() {
        return memberMapper.findAll();
    }

    @Override
    public MemberDto findById(Long id) {
        return memberMapper.findById(id);
    }

    @Override
    public void remove(Long id) {
        memberMapper.remove(id);
    }

    @Override
    public MemberDto modify(MemberDto memberDto) {
        memberMapper.modify(memberDto);
        return memberMapper.findById(memberDto.getId());
    }

    @Override
    public Map<String, String> usernameCheck(String username) {
        Map<String, String> returnMap = new HashMap<>();

//        int usernameCheck = memberMapper.usernameCheck(username);
        long usernameCheck = memberRepository.countByUsername(username);

        if(usernameCheck != 0) {
            throw new RuntimeException("username duplicated");
        }

        returnMap.put("usernameCheckMsg", "usernameOk");

        return returnMap;
    }

    @Override
    public Map<String, String> nicknameCheck(String nickname) {
        Map<String, String> returnMap = new HashMap<>();

//        int nicknameCheck = memberMapper.nicknameCheck(nickname);
        long nicknameCheck = memberRepository.countByNickname(nickname);

        if(nicknameCheck != 0) {
            throw new RuntimeException("nickname duplicated");
        }

        returnMap.put("nicknameCheckMsg", "nicknameOk");

        return returnMap;
    }

    @Override
    public MemberDto join(MemberDto memberDto) {
        System.out.println(memberDto.getPassword());
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        return memberRepository.save(memberDto.toEntity()).toDto();
    }

    @Override
    public MemberDto login(MemberDto memberDto) {

        long usernameCheck = memberRepository.countByUsername(memberDto.getUsername());
//        int usernameCheck = memberMapper.usernameCheck(memberDto.getUsername());

        if(usernameCheck == 0) {
            throw new RuntimeException("id not exist");
        }

//        MemberDto loginMember = memberMapper.findByIdAndPassword(memberDto);
        Optional<Member> loginMember = memberRepository.findByUsernameAndPassword(memberDto.getUsername(), memberDto.getPassword());

//        if(loginMember == null) {
//            throw new RuntimeException("wrong password");
//        }

        return loginMember.orElseThrow(() -> new RuntimeException("wrong password"))
                          .toDto();
    }
}
