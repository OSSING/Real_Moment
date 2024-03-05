package com.project.Real_Moment.domain.member.repository.custom;

import com.project.Real_Moment.domain.member.entity.Member;
import com.project.Real_Moment.domain.member.entity.Review;
import com.project.Real_Moment.presentation.dto.ReviewDto;

public interface ReviewRepositoryCustom {

    ReviewDto.ItemDetReviewResponse findReviewListByItemIdOrStar(Long id, Integer star, int nowPage);

    ReviewDto.MyReviewListResponse findMyReviewListByMemberId(Member member, int nowPage);

    void updateReview(ReviewDto.editReviewClick dto);
}
