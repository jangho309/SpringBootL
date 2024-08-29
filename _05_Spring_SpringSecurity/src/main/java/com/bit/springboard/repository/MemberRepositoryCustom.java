package com.bit.springboard.repository;

import com.bit.springboard.entity.Member;

public interface MemberRepositoryCustom {
    Member searchOne(Long id);
    String searchUsername(Long id);
}
