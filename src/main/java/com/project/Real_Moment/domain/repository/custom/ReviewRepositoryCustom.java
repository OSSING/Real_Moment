package com.project.Real_Moment.domain.repository.custom;

import com.project.Real_Moment.domain.entity.Member;
import com.project.Real_Moment.domain.entity.Review;
import com.project.Real_Moment.presentation.dto.CondDto;
import com.project.Real_Moment.presentation.dto.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {

    ReviewDto.ItemDetReviewResponse findReviewListByItemIdOrStar(Long id, Integer star, int nowPage);


    Page<Review> findMyReviewListByMemberId(Pageable pageable, Member member);


    void updateReview(ReviewDto.editReviewClick dto);

    Page<Review> findReviewListByCond(Pageable pageable, CondDto.ReviewListCond dto);
}
