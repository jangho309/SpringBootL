package com.bit.springboard.repository.impl;

import com.bit.springboard.entity.Notice;
import com.bit.springboard.repository.NoticeRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.bit.springboard.entity.QNotice.notice;

@Repository
@RequiredArgsConstructor
public class NoticeRepositoryCustomImpl implements NoticeRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Notice> searchAll(Pageable pageable, Map<String, String> searchMap) {
        List<Notice> noticeList =
                jpaQueryFactory.selectFrom(notice)
                        .where(searchCondition(searchMap))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        long total = jpaQueryFactory.select(notice.count())
                        .from(notice)
                        .where(searchCondition(searchMap))
                        .fetchOne();

        return new PageImpl<>(noticeList, pageable, total);
    }

    @Override
    public Optional<Notice> searchOne(Long id) {
        Notice searchNotice =
            jpaQueryFactory.selectFrom(notice)
                           .where(notice.id.eq(id))
                           .fetchOne();

//        if(searchNotice == null) {
//            return Optional.empty();
//        }
//
//        return Optional.of(searchNotice);
        return Optional.ofNullable(searchNotice);
    }

    public BooleanBuilder searchCondition(Map<String, String> searchMap) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 검색어가 없을 때는 where절 실행 안됨
        if(searchMap.get("searchKeyword") == null){
            return null;
        }

        booleanBuilder.or(notice.title.containsIgnoreCase(searchMap.get("searchKeyword")));
        booleanBuilder.or(notice.content.containsIgnoreCase(searchMap.get("searchKeyword")));
        booleanBuilder.or(notice.member.nickname.containsIgnoreCase(searchMap.get("searchKeyword")));

        return booleanBuilder;
    }
}
