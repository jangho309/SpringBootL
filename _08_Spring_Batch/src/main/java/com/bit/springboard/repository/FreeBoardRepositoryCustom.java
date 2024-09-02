package com.bit.springboard.repository;

import com.bit.springboard.entity.FreeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;

public interface FreeBoardRepositoryCustom {
    Page<FreeBoard> searchAll(Pageable pageable, Map<String, String> searchMap);

    Optional<FreeBoard> searchOne(Long id);
}
