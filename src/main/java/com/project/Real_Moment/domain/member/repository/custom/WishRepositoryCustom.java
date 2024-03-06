package com.project.Real_Moment.domain.member.repository.custom;

import com.project.Real_Moment.domain.member.entity.Wish;
import com.project.Real_Moment.presentation.dto.WishDto;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WishRepositoryCustom {

    Page<Wish> findWishByMemberIdPaging(Pageable pageable, Long id, int nowPage);
}
