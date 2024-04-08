package com.project.Real_Moment.domain.repository.impl;

import com.project.Real_Moment.domain.entity.Review;
import com.project.Real_Moment.domain.entity.Member;
import com.project.Real_Moment.domain.repository.custom.ReviewRepositoryCustom;
import com.project.Real_Moment.presentation.dto.CondDto;
import com.project.Real_Moment.presentation.dto.ItemDto;
import com.project.Real_Moment.presentation.dto.ReviewDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.project.Real_Moment.domain.entity.QItemQA.itemQA;
import static com.project.Real_Moment.domain.entity.QReview.review;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Review> findMyReviewListByPaging(Pageable pageable, CondDto.ReviewListCond requestDto) {
        List<Review> reviewList = queryFactory
                .selectFrom(review)
                .where(starEq(requestDto.getStar()),
                        review.itemId.id.eq(requestDto.getItemId()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(review.count())
                .from(review)
                .where(starEq(requestDto.getStar()),
                        review.itemId.id.eq(requestDto.getItemId()))
                .fetchOne();

        return new PageImpl<>(reviewList, pageable, total);
    }

    @Override
    public Page<Review> findMyReviewListByMemberId(Pageable pageable, Member member) {

        List<Review> reviewList = queryFactory
                .selectFrom(review)
                .where(review.memberId.eq(member))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(review.count())
                .from(review)
                .where(review.memberId.eq(member))
                .fetchOne();

        return new PageImpl<>(reviewList, pageable, total);
    }

    @Override
    public void updateReview(ReviewDto.editReviewClick dto) {
        queryFactory
                .update(review)
                .set(review.title, dto.getTitle())
                .set(review.content, dto.getContent())
                .set(review.star, dto.getStar())
                .where(review.id.eq(dto.getReviewId()))
                .execute();
    }

    @Override
    public Page<Review> findReviewListByCond(Pageable pageable, CondDto.ReviewListCond dto) {
        List<Review> reviewList = queryFactory
                .selectFrom(review)
                .where(itemIdEq(dto.getItemId()),
                        starEq(dto.getStar()))
                .orderBy(review.createdDate.desc().nullsLast())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(review.count())
                .from(review)
                .where(itemIdEq(dto.getItemId()),
                        starEq(dto.getStar()))
                .fetchOne();

        return new PageImpl<>(reviewList, pageable, total);
    }

    private BooleanExpression itemIdEq(Long itemId) {
        return itemId != null ? review.itemId.id.eq(itemId) : null;
    }

    private BooleanExpression starEq(Integer star) {
        return star != null ? review.star.eq(star) : null;
    }
}
