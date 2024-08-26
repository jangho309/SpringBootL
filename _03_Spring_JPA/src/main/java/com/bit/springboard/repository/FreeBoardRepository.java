package com.bit.springboard.repository;

import com.bit.springboard.entity.FreeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {
    /**
     *
     * @param title
     * @return
     * SELECT ID
     *      , TITLE
     *      , CONTENT
     *      , WRITER_ID
     *      , REGDATE
     *      , MODDATE
     *      , CNT
     *   FROM FREEBOARD
     *  WHERE TITLE = #{title}
     */
    Optional<FreeBoard> findByTitle(String title);

    List<FreeBoard> findByMemberUsername(String name);

    Page<FreeBoard> findByTitleContainingOrContentContainingOrMemberNicknameContaining(Pageable pageable, String searchKeyworkd, String searchKeyword, String searchKeyword1);

    Page<FreeBoard> findByTitleContaining(Pageable pageable,String searchKeyword);

    Page<FreeBoard> findByContentContaining(Pageable pageable,String searchKeyword);

    Page<FreeBoard> findByMemberNicknameContaining(Pageable pageable,String searchKeyword);
}
