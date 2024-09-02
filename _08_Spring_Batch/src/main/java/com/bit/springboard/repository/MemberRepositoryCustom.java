package com.bit.springboard.repository;

import com.bit.springboard.entity.Member;

import java.time.LocalDateTime;
import java.util.List;

public interface MemberRepositoryCustom {
    Member searchOne(Long id);
    String searchUsername(Long id);
    List<Member> findInactiveMembers(LocalDateTime oneYearAgo);
}
