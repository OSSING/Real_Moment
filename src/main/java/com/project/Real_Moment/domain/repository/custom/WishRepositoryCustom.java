package com.project.Real_Moment.domain.repository.custom;

import com.project.Real_Moment.domain.entity.Wish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WishRepositoryCustom {

    Page<Wish> findWishByMemberIdPaging(Pageable pageable, Long id, int nowPage);
}
