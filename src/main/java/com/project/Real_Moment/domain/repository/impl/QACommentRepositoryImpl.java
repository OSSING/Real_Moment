package com.project.Real_Moment.domain.repository.impl;

import com.project.Real_Moment.domain.entity.QQAComment;
import com.project.Real_Moment.domain.repository.custom.QACommentRepositoryCustom;
import com.project.Real_Moment.presentation.dto.QACommentDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.project.Real_Moment.domain.entity.QQAComment.qAComment;

@RequiredArgsConstructor
public class QACommentRepositoryImpl implements QACommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void updateById(QACommentDto.EditQAComment dto) {
        queryFactory
                .update(qAComment)
                .set(qAComment.content, dto.getContent())
                .where(qAComment.id.eq(dto.getQaCommentId()))
                .execute();
    }
}
