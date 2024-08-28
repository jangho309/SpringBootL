package com.bit.springboard.service;

import com.bit.springboard.dto.MemberDto;

import java.util.List;
import java.util.Map;

public interface MemberService {
    MemberDto save(MemberDto memberDto);

    List<MemberDto> findAll();

    MemberDto findById(Long id);

    void remove(Long id);

    MemberDto modify(MemberDto memberDto);

    Map<String, String> usernameCheck(String username);

    Map<String, String> nicknameCheck(String nickname);

    MemberDto join(MemberDto memberDto);

    MemberDto login(MemberDto memberDto);
}
