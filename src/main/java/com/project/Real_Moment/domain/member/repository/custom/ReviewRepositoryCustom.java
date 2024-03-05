package com.project.Real_Moment.domain.member.repository.custom;

import com.project.Real_Moment.presentation.dto.ReviewDto;

public interface ReviewRepositoryCustom {

    ReviewDto.ItemDetReviewResponse findReviewListByItemIdOrStar(Long id, Integer star, int nowPage);
}
