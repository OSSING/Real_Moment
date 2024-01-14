package com.project.shoppingmall.repository;

import com.project.shoppingmall.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewsRepository extends JpaRepository<Review, Long> {
}
