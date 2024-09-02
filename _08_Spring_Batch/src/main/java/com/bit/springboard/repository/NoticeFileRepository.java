package com.bit.springboard.repository;

import com.bit.springboard.dto.BoardFileDto;
import com.bit.springboard.entity.NoticeFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeFileRepository extends JpaRepository<NoticeFile, Long> {
    List<NoticeFile> findByNoticeId(Long id);
}
