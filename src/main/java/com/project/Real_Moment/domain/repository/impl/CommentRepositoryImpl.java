package com.project.Real_Moment.domain.repository.impl;

import com.project.Real_Moment.domain.entity.Admin;
import com.project.Real_Moment.domain.repository.custom.CommentRepositoryCustom;
import com.project.Real_Moment.presentation.dto.CommentDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.project.Real_Moment.domain.entity.QComment.comment;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void updateComment(CommentDto.CommentResponse dto) {
        queryFactory
                .update(comment)
                .set(comment.content, dto.getContent())
                .where(comment.id.eq(dto.getCommentId()))
                .execute();
    }
}
