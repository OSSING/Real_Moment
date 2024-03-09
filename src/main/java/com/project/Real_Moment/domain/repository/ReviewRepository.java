package com.project.Real_Moment.domain.repository;

import com.project.Real_Moment.domain.entity.Item;
import com.project.Real_Moment.domain.entity.Member;
import com.project.Real_Moment.domain.entity.Review;
import com.project.Real_Moment.domain.repository.custom.ReviewRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
    boolean existsByMemberIdAndItemId(Member memberId, Item itemId);

    boolean existsByIdAndMemberId(Long reviewId, Member memberId);
}
