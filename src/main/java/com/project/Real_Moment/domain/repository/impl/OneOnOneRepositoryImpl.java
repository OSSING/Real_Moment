package com.project.Real_Moment.domain.repository.impl;

import com.project.Real_Moment.domain.entity.OneOnOne;
import com.project.Real_Moment.domain.repository.custom.OneOnOneRepositoryCustom;
import com.project.Real_Moment.presentation.dto.CondDto;
import com.project.Real_Moment.presentation.dto.OneOnOneDto;
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
                        answerEq(dto.getIsAnswer()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(oneOnOne.count())
                .from(oneOnOne)
                .where(oneOnOne.memberId.id.eq(memberId),
                        answerEq(dto.getIsAnswer()))
                .fetchOne();

        return new PageImpl<>(oneOnOneList, pageable, total);
    }

    @Override
    public void updateOneOnOne(Long memberId, OneOnOneDto.editOneOnOneClick dto) {
        queryFactory
                .update(oneOnOne)
                .set(oneOnOne.title, dto.getTitle())
                .set(oneOnOne.content, dto.getContent())
                .where(oneOnOne.id.eq(dto.getOneOnOneId()),
                        oneOnOne.memberId.id.eq(memberId))
                .execute();
    }

    @Override
    public Page<OneOnOne> findOneOnOneListByPaging_admin(Pageable pageable, CondDto.AdminOneOnOneListCond dto) {
        List<OneOnOne> oneOnOneList = queryFactory
                .selectFrom(oneOnOne)
                .where(answerEq(dto.getIsAnswer()),
                        loginIdEq(dto.getLoginId()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(oneOnOne.count())
                .from(oneOnOne)
                .where(answerEq(dto.getIsAnswer()),
                        loginIdEq(dto.getLoginId()))
                .fetchOne();

        return new PageImpl<>(oneOnOneList, pageable, total);
    }

    @Override
    public void updateOneOnOneByComment(Long oneOnOneId) {
        queryFactory
                .update(oneOnOne)
                .set(oneOnOne.isAnswer, true)
                .where(oneOnOne.id.eq(oneOnOneId))
                .execute();
    }

    @Override
    public void updateOneOnOneById(Long oneOnOneId) {
        queryFactory
                .update(oneOnOne)
                .set(oneOnOne.isAnswer, false)
                .where(oneOnOne.id.eq(oneOnOneId))
                .execute();
    }

    private BooleanExpression answerEq(Boolean answer) {
        return answer != null ? oneOnOne.isAnswer.eq(answer) : null;
    }

    private BooleanExpression loginIdEq(String loginId) {
        return loginId != null ? oneOnOne.memberId.loginId.eq(loginId) : null;
    }
}
