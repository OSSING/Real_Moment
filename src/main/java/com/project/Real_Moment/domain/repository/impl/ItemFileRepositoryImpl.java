package com.project.Real_Moment.domain.repository.impl;

import com.project.Real_Moment.domain.repository.custom.ItemFileRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ItemFileRepositoryImpl implements ItemFileRepositoryCustom {

    private final JPAQueryFactory queryFactory;
}
