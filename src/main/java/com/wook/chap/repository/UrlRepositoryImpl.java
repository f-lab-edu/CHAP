package com.wook.chap.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wook.chap.model.dto.SearchConditionDto;
import com.wook.chap.model.dto.SearchConditionInfo;
import com.wook.chap.model.dto.UrlInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static com.querydsl.core.types.Projections.*;
import static com.wook.chap.model.entity.QUrl.url;
import static org.springframework.util.StringUtils.*;

public class UrlRepositoryImpl implements UrlRepositoryCustom{

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

    public UrlRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<UrlInfo> searchPage(Long memberId, SearchConditionInfo searchCondition, Pageable pageable) {

        System.out.println("hasText :"+hasText(searchCondition.getSearchWord()));

        List<UrlInfo> contents = queryFactory.select(constructor(UrlInfo.class, url.id, url.createTime, url.originalURL, url.shortURL, url.clickCount))
                .from(url)
                .where(url.member.id.eq(memberId),
                        searchWordEq(searchCondition.getSearchWord()),
                        dateGoe(searchCondition.getStartDate()),
                        dateLoe(searchCondition.getEndDate()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(urlInfoSort(pageable))
                .fetch();

        Long total = queryFactory.select(url.count()).from(url)
                .where(url.member.id.eq(memberId),
                        searchWordEq(searchCondition.getSearchWord()),
                        dateGoe(searchCondition.getStartDate()),
                        dateLoe(searchCondition.getEndDate()))
                .fetchFirst();

        return new PageImpl<>(contents, pageable, total);
    }

    private BooleanExpression searchWordEq(String searchWord) {
        return hasText(searchWord) ? url.originalURL.containsIgnoreCase(searchWord) : null;
    }

    private BooleanExpression dateGoe(LocalDateTime startTime) {
        return startTime==null ? null : url.createTime.goe(startTime);
    }

    private BooleanExpression dateLoe(LocalDateTime endTime) {
        return endTime==null ? null : url.createTime.loe(endTime);
    }

    private OrderSpecifier<?> urlInfoSort(Pageable pageable) {

        Sort sort = pageable.getSort();

        if (!sort.isEmpty()) {
            for (Sort.Order order : sort) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

                switch (order.getProperty()) {
                    case "createdTime":
                        return new OrderSpecifier<>(direction, url.createTime);
                }
            }
        }

        return null;
    }
}
