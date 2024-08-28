package com.bit.springboard.repository;

import com.bit.springboard.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    List<Member> findByUsernameAndEmail(String username, String email);

    List<Member> findByUsernameOrEmail(String username, String email);

    List<Member> findByUsernameStartingWith(String prefix);

    List<Member> findByUsernameEndingWith(String suffix);

    List<Member> findByUsernameContaining(String username);

    List<Member> findByIdBetween(long startId, long endId);

    Page<Member> findAllByOrderByIdDesc(Pageable pageable);

    @Query("select count(m.username) from Member m where m.username = :username")
    long countByUsername(@Param("username") String username);

    long countByNickname(String nickname);

    Optional<Member> findByUsernameAndPassword(String username, String password);

    Optional<Member> findByNickname(String nickname);

    @Query("select m from Member m where m.email = :email")
    List<Member> findByEmail(@Param("email") String email);

    @Query("select m from Member m where m.id > :id and m.nickname like concat('%', :nickname, '%')")
    List<Member> findBiggerThanNicknameContaining(@Param("id") Long id,
                                                  @Param("nickname") String nickname);

    @Query("select m from Member m")
    Page<Member> findAll(Pageable pageable);

    // 페이징 처리를 하는 네이티브 쿼리를 작성할 때는
    // 카운트 쿼리가 자동으로 동작하지 않기 때문에
    // 카운트 쿼리를 따로 작성해야된다.
    @Query(value = "select * from Member m",
            countQuery = "select count(*) from Member",
            nativeQuery = true)
    Page<Member> findAllMembers(Pageable pageable);
}
