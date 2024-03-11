package com.project.Real_Moment.domain.repository.impl;

import com.project.Real_Moment.domain.entity.Review;
import com.project.Real_Moment.domain.entity.Member;
import com.project.Real_Moment.domain.repository.custom.ReviewRepositoryCustom;
import com.project.Real_Moment.presentation.dto.ItemDto;
import com.project.Real_Moment.presentation.dto.ReviewDto;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.project.Real_Moment.domain.entity.QReview.review;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public ReviewDto.ItemDetReviewResponse findReviewListByItemIdOrStar(Long id, Integer star, int nowPage) {
        Pageable pageable = PageRequest.of(nowPage - 1, 5);

        JPAQuery<Review> query = queryFactory
                .selectFrom(review)
                .where(review.itemId.id.eq(id));

        JPAQuery<Long> countQuery = queryFactory
                .select(review.count())
                .from(review)
                .where(review.itemId.id.eq(id));

        if (star != null) {
            query.where(review.star.eq(star));
            countQuery.where(review.star.eq(star));
        }

        List<ReviewDto.ReviewListResponse> results = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
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

        Long total = countQuery.fetchOne();

//        List<ReviewDto.ReviewListResponse> reviewList = results.getResults().stream()
//                .map(review -> new ReviewDto.ReviewListResponse(
//                        review.getId(),
//                        review.getMemberId().getLoginId(),
//                        review.getTitle(),
//                        review.getContent(),
//                        review.getStar(),
//                        review.getCreatedDate(),
//                        review.getLastModifiedDate()
//                ))
//                .toList();

        int totalPages = (int) Math.ceil((double) total / pageable.getPageSize());
        return new ReviewDto.ItemDetReviewResponse(results, totalPages, nowPage);

    }

    @Override
    public ReviewDto.MyReviewListResponse findMyReviewListByMemberId(Member member, int nowPage) {
        Pageable pageable = PageRequest.of(nowPage - 1, 10);

        List<ReviewDto.MyReview> results = queryFactory.selectFrom(review)
                .where(review.memberId.eq(member))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(review -> new ReviewDto.MyReview(
                        review.getId(),
                        new ItemDto.ItemResponse(
                                review.getItemId().getId(),
                                review.getItemId().getName(),
                                review.getItemId().getPrice(),
                                review.getItemId().getDiscountRate(),
                                review.getItemId().getDiscountPrice(),
                                review.getItemId().getSellPrice(),
                                review.getItemId().isSell(),
                                review.getItemId().getMainImg()
                        ),
                        review.getMemberId().getLoginId(),
                        review.getTitle(),
                        review.getContent(),
                        review.getStar(),
                        review.getCreatedDate(),
                        review.getLastModifiedDate()
                ))
                .toList();

        Long total = queryFactory
                .select(review.count())
                .from(review)
                .where(review.memberId.eq(member))
                .fetchOne();

        int totalPages = (int) Math.ceil((double) total / pageable.getPageSize());
        return new ReviewDto.MyReviewListResponse(results, totalPages, nowPage);
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
}
