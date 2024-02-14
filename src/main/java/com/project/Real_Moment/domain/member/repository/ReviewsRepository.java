package com.project.Real_Moment.domain.member.repository;

import com.project.Real_Moment.domain.member.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewsRepository extends JpaRepository<Review, Long> {
}
