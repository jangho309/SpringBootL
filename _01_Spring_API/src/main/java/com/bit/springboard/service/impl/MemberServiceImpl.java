package com.bit.springboard.service.impl;

import com.bit.springboard.dto.MemberDto;
import com.bit.springboard.mapper.MemberMapper;
import com.bit.springboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public MemberDto modify(MemberDto memberDto) {
        memberMapper.modify(memberDto);
        return memberMapper.findById(memberDto.getId());
    }

    @Override
    public void remove(int id){
        memberMapper.remove(id);
    }
}
