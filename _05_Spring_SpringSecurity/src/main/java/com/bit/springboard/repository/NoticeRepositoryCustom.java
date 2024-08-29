package com.bit.springboard.repository;

import com.bit.springboard.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;

public interface NoticeRepositoryCustom {
    Page<Notice> searchAll(Pageable pageable, Map<String, String> searchMap);

    // return Optional.of(객체)
    Optional<Notice> searchOne(Long id);
}
