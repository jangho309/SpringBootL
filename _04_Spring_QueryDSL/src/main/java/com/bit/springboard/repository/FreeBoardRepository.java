package com.bit.springboard.repository;

import com.bit.springboard.entity.FreeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long>, FreeBoardRepositoryCustom {
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

    // 쿼리 메소드명이 길어지는 쿼리들은
    // @Query를 이용해서 JPQL이나 네이티브 쿼리로 작성해주는게 편하다.
    @Query("select f from FreeBoard f " +
           " inner join f.member m" +
           " where f.title like concat('%', :searchKeyword, '%')" +
           "    or f.content like concat('%', :searchKeyword, '%')" +
           "    or m.nickname like concat('%', :searchKeyword, '%')")
    Page<FreeBoard> findBySearchKeyword(Pageable pageable,
                                        @Param("searchKeyword") String searchKeyword);

    Page<FreeBoard> findByTitleContainingOrContentContainingOrMemberNicknameContaining(Pageable pageable, String searchKeyworkd, String searchKeyword, String searchKeyword1);

    Page<FreeBoard> findByTitleContaining(Pageable pageable,String searchKeyword);

    Page<FreeBoard> findByContentContaining(Pageable pageable,String searchKeyword);

    Page<FreeBoard> findByMemberNicknameContaining(Pageable pageable,String searchKeyword);

    /*
    * 네이티브 쿼리 작성
    * countQuery 있어야 페이징처리가 가능함
    * */
    @Query(value = "select * from FreeBoard"
            , countQuery = "select count(*) from FreeBoard"
            , nativeQuery = true)
    Page<FreeBoard> findAllFreeBoard(Pageable pageable);
}
