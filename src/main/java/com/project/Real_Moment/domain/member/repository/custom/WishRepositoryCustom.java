package com.project.Real_Moment.domain.member.repository.custom;

import com.project.Real_Moment.presentation.dto.WishDto;
import com.querydsl.core.Tuple;

import java.util.List;

public interface WishRepositoryCustom {

    List<WishDto.WishListResponse> findWishByMemberId(Long id);
}
