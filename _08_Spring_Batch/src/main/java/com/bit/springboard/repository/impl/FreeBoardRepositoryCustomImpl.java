package com.bit.springboard.repository.impl;

import com.bit.springboard.entity.FreeBoard;
import com.bit.springboard.entity.QFreeBoard;
import com.bit.springboard.repository.FreeBoardRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.bit.springboard.entity.QFreeBoard.freeBoard;
import static com.bit.springboard.entity.QFreeBoardFile.freeBoardFile;
import static com.bit.springboard.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class FreeBoardRepositoryCustomImpl implements FreeBoardRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<FreeBoard> searchAll(Pageable pageable, Map<String, String> searchMap) {
        List<FreeBoard> freeBoardList =
                jpaQueryFactory.selectFrom(freeBoard)
                        .where(searchCondition(searchMap))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        long total = jpaQueryFactory.select(freeBoard.count())
                        .from(freeBoard)
                        .where(searchCondition((searchMap)))
                        .fetchOne();

        return new PageImpl<>(freeBoardList, pageable, total);
    }

    @Override
    public Optional<FreeBoard> searchOne(Long id) {
        FreeBoard searchFreeBoard = jpaQueryFactory
                .selectFrom(freeBoard)
                .where(freeBoard.id.eq(id))
                .fetchOne();

        searchFreeBoard.setMember(jpaQueryFactory.select(member)
                .from(member)
                .join(member, freeBoard.member)
                .where(freeBoard.id.eq(id))
                .fetchOne());

        searchFreeBoard.setBoardFileList(jpaQueryFactory.selectFrom(freeBoardFile)
                .where(freeBoardFile.freeBoard.id.eq(id)).fetch());

        return Optional.ofNullable(searchFreeBoard);
    }

    public BooleanBuilder searchCondition(Map<String, String> searchMap) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 검색어가 없을 때는 where절 실행 안됨
        if(searchMap.get("searchKeyword") == null){
            return null;
        }

        if(searchMap.get("searchCondition").equalsIgnoreCase("all")){
            booleanBuilder.or(freeBoard.title.containsIgnoreCase(searchMap.get("searchKeyword")));
            booleanBuilder.or(freeBoard.content.containsIgnoreCase(searchMap.get("searchKeyword")));
            booleanBuilder.or(freeBoard.member.nickname.containsIgnoreCase(searchMap.get("searchKeyword")));
        } else if(searchMap.get("searchCondition").equalsIgnoreCase("title")){
            booleanBuilder.and(freeBoard.title.containsIgnoreCase(searchMap.get("searchKeyword")));
        } else if(searchMap.get("searchCondition").equalsIgnoreCase("content")){
            booleanBuilder.and(freeBoard.content.containsIgnoreCase(searchMap.get("searchKeyword")));
        } else if(searchMap.get("searchCondition").equalsIgnoreCase("writer")){
            booleanBuilder.and(freeBoard.member.nickname.containsIgnoreCase(searchMap.get("searchKeyword")));
        }

        return booleanBuilder;
    }
}
