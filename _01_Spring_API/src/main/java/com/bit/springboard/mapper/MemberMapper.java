package com.bit.springboard.mapper;

import com.bit.springboard.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    void save(MemberDto memberDto);

    MemberDto findLastMember();

    List<MemberDto> findAll();

    MemberDto findById(int id);

    void modify(MemberDto memberDto);

    void remove(int id);
}
