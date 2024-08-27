package com.bit.springboard.repository;

import com.bit.springboard.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsernameAndEmail(String username, String email);

    List<Member> findByUsernameOrEmail(String username, String email);

    List<Member> findByUsernameStartingWith(String prefix);

    List<Member> findByUsernameEndingWith(String suffix);

    List<Member> findByUsernameContaining(String username);

    List<Member> findByIdBetween(long startId, long endId);

    Page<Member> findAllByOrderByIdDesc(Pageable pageable);

    long countByUsername(String username);

    long countByNickname(String nickname);

    Optional<Member> findByUsernameAndPassword(String username, String password);

    Optional<Member> findByNickname(String nickname);
}
