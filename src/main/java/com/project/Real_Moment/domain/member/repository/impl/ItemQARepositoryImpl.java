package com.project.Real_Moment.domain.member.repository.impl;

import com.project.Real_Moment.domain.member.entity.ItemQA;
import com.project.Real_Moment.domain.member.repository.custom.ItemQARepositoryCustom;
import com.project.Real_Moment.presentation.dto.CondDto;
import com.project.Real_Moment.presentation.dto.ItemQADto;
import com.project.Real_Moment.presentation.dto.QACommentDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.project.Real_Moment.domain.member.entity.QItemQA.itemQA;

@RequiredArgsConstructor
public class ItemQARepositoryImpl implements ItemQARepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<ItemQA> findQAListByCond(Pageable pageable, CondDto.QAListCond dto) {

        List<ItemQA> itemQAList = queryFactory
                .selectFrom(itemQA)
                .where(itemIdEq(dto.getItemId()),
                        answerEq(dto.getIsAnswer()),
                        itemQA.itemId.isDelete.eq(false))
                .orderBy(itemQA.createdDate.desc().nullsLast())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(itemQA.count())
                .from(itemQA)
                .where(itemIdEq(dto.getItemId()),
                        answerEq(dto.getIsAnswer()),
                        itemQA.itemId.isDelete.eq(false))
                .fetchOne();

        return new PageImpl<>(itemQAList, pageable, total);

    }

    private BooleanExpression itemIdEq(Long itemId) {
        return itemId != null ? itemQA.itemId.id.eq(itemId) : null;
    }

    private BooleanExpression answerEq(Boolean isAnswer) {
        return isAnswer != null ? itemQA.isAnswer.eq(isAnswer) : null;
    }
}
