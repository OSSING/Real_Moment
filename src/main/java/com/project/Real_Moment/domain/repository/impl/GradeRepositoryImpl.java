package com.project.Real_Moment.domain.repository.impl;

import com.project.Real_Moment.domain.entity.QGrade;
import com.project.Real_Moment.domain.repository.custom.GradeRepositoryCustom;
import com.project.Real_Moment.presentation.dto.GradeDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.project.Real_Moment.domain.entity.QGrade.grade;

@RequiredArgsConstructor
public class GradeRepositoryImpl implements GradeRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public void updateGrade(GradeDto.EditGrade dto) {
        queryFactory
                .update(grade)
                .set(grade.gradeName, dto.getGradeName())
                .set(grade.rewardRate, dto.getRewardRate())
                .where(grade.id.eq(dto.getGradeId()))
                .execute();
    }
}
