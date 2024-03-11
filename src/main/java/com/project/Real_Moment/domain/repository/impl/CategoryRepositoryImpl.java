package com.project.Real_Moment.domain.repository.impl;

import com.project.Real_Moment.domain.repository.custom.CategoryRepositoryCustom;
import com.project.Real_Moment.presentation.dto.CategoryDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.project.Real_Moment.domain.entity.QCategory.category;

@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void updateCategory(CategoryDto.CategoryList dto) {

        if (dto.getParentId() != null) {
            queryFactory
                    .update(category)
                    .set(category.name, dto.getName())
                    .set(category.parent.id, dto.getParentId())
                    .where(category.id.eq(dto.getCategoryId()))
                    .execute();
        } else {
            queryFactory
                    .update(category)
                    .set(category.name, dto.getName())
                    .where(category.id.eq(dto.getCategoryId()))
                    .execute();
        }
    }
}
