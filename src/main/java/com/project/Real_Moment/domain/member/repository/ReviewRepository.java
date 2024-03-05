package com.project.Real_Moment.domain.member.repository;

import com.project.Real_Moment.domain.member.entity.Review;
import com.project.Real_Moment.domain.member.repository.custom.ReviewRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
}
