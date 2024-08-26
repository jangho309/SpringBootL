package com.bit.springboard.service;

import com.bit.springboard.dto.BoardDto;
import com.bit.springboard.dto.MemberDto;
import com.bit.springboard.entity.FreeBoard;
import com.bit.springboard.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ApiService {
    Member save(MemberDto memberDto);

    Page<Member> findAll(Pageable pageable);

    Member findById(Long id);

    void deleteById(Long id);

    Member modify(Long id, MemberDto memberDto);

    FreeBoard post(BoardDto boardDto, MultipartFile[] uploadFiles);

    FreeBoard findFreeBoardById(Long id);

    FreeBoard findByTitle(String title);

    List<Member> findByUsernameAndEmail(String username, String email);

    List<Member> findByUsernameOrEmail(String username, String email);

    public List<Member> findByUsernameLike(String username);

    List<Member> findByIdBetween(Long startId, Long endId);

    List<FreeBoard> findByMemberUsername(String username);
}
