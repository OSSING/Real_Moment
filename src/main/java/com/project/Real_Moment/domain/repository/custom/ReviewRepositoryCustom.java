package com.project.Real_Moment.domain.repository.custom;

import com.project.Real_Moment.domain.entity.Member;
import com.project.Real_Moment.presentation.dto.ReviewDto;

public interface ReviewRepositoryCustom {

    ReviewDto.ItemDetReviewResponse findReviewListByItemIdOrStar(Long id, Integer star, int nowPage);

    ReviewDto.MyReviewListResponse findMyReviewListByMemberId(Member member, int nowPage);

    void updateReview(ReviewDto.editReviewClick dto);
}
