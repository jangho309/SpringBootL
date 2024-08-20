package com.bit.springboard.service.impl;

import com.bit.springboard.dto.MemberDto;
import com.bit.springboard.mapper.MemberMapper;
import com.bit.springboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;

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
    public MemberDto findById(int id) {
        return memberMapper.findById(id);
    }

    @Override
    public void remove(int id) {
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

        int usernameCheck = memberMapper.usernameCheck(username);

        if(usernameCheck != 0) {
            throw new RuntimeException("username duplicated");
        }

        returnMap.put("usernameCheckMsg", "usernameOk");

        return returnMap;
    }

    @Override
    public Map<String, String> nicknameCheck(String nickname) {
        Map<String, String> returnMap = new HashMap<>();

        int nicknameCheck = memberMapper.nicknameCheck(nickname);

        if(nicknameCheck != 0) {
            throw new RuntimeException("nickname duplicated");
        }

        returnMap.put("nicknameCheckMsg", "nicknameOk");

        return returnMap;
    }

    @Override
    public void join(MemberDto memberDto) {
        memberMapper.save(memberDto);
    }

    @Override
    public MemberDto login(MemberDto memberDto) {
        int usernameCheck = memberMapper.usernameCheck(memberDto.getUsername());

        if(usernameCheck == 0) {
            throw new RuntimeException("id not exist");
        }

        MemberDto loginMember = memberMapper.findByIdAndPassword(memberDto);

        if(loginMember == null) {
            throw new RuntimeException("wrong password");
        }

        return loginMember;
    }
}
