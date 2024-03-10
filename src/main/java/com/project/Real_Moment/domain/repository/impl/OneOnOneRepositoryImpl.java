package com.project.Real_Moment.domain.repository.impl;

import com.project.Real_Moment.domain.entity.OneOnOne;
import com.project.Real_Moment.domain.repository.custom.OneOnOneRepositoryCustom;
import com.project.Real_Moment.presentation.dto.CondDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.project.Real_Moment.domain.entity.QOneOnOne.oneOnOne;

@RequiredArgsConstructor
public class OneOnOneRepositoryImpl implements OneOnOneRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<OneOnOne> findOneOnOneListByPaging(Long memberId, CondDto.OneOnOneListCond dto, Pageable pageable) {
        List<OneOnOne> oneOnOneList = queryFactory
                .selectFrom(oneOnOne)
                .where(oneOnOne.memberId.id.eq(memberId),
                        answerEq(dto.getAnswer()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(oneOnOne.count())
                .from(oneOnOne)
                .where(oneOnOne.memberId.id.eq(memberId),
                        answerEq(dto.getAnswer()))
                .fetchOne();

        return new PageImpl<>(oneOnOneList, pageable, total);
    }

    private BooleanExpression answerEq(Boolean answer) {
        return answer != null ? oneOnOne.isAnswer.eq(answer) : null;
    }
}
