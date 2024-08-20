package com.bit.springboard.service;

import com.bit.springboard.dto.MemberDto;

import java.util.List;

public interface MemberService {
    MemberDto save(MemberDto memberDto);

    List<MemberDto> findAll();

    MemberDto findById(int id);

    MemberDto modify(MemberDto memberDto);

    void remove(int id);
}
