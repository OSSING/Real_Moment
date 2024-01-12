package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewsRepository extends JpaRepository<Review, Long> {
}
