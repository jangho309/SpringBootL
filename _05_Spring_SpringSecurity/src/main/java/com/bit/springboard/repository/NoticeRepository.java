package com.bit.springboard.repository;

import com.bit.springboard.dto.BoardFileDto;
import com.bit.springboard.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long>, NoticeRepositoryCustom {
    Page<Notice> findByTitleContainingOrContentContainingOrMemberNicknameContaining(Pageable pageable, String searchKeyworkd, String searchKeyword, String searchKeyword1);

    Page<Notice> findByTitleContaining(Pageable pageable, String searchKeyword);

    Page<Notice> findByContentContaining(Pageable pageable, String searchKeyword);

    Page<Notice> findByMemberNicknameContaining(Pageable pageable, String searchKeyword);
}
