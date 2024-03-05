package com.project.Real_Moment.domain.member.repository.impl;

import com.project.Real_Moment.domain.member.entity.Review;
import com.project.Real_Moment.domain.member.repository.custom.ReviewRepositoryCustom;
import com.project.Real_Moment.presentation.dto.ReviewDto;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.project.Real_Moment.domain.member.entity.QReview.review;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public ReviewDto.ItemDetReviewResponse findReviewListByItemIdOrStar(Long id, Integer star, int nowPage) {
        Pageable pageable = PageRequest.of(nowPage - 1, 5);

        JPAQuery<Review> query = queryFactory
                .selectFrom(review)
                .where(review.itemId.id.eq(id));

        if (star != null) {
            query.where(review.star.eq(star));
        }

        QueryResults<Review> results = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ReviewDto.ReviewListResponse> reviewList = results.getResults().stream()
                .map(review -> new ReviewDto.ReviewListResponse(
                        review.getId(),
                        review.getMemberId().getLoginId(),
                        review.getTitle(),
                        review.getContent(),
                        review.getStar(),
                        review.getCreatedDate(),
                        review.getLastModifiedDate()
                ))
                .toList();

        return new ReviewDto.ItemDetReviewResponse(reviewList, results.getTotal(), nowPage);

    }
}
