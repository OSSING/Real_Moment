package com.project.Real_Moment.repository;

import com.project.Real_Moment.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewsRepository extends JpaRepository<Review, Long> {
}
