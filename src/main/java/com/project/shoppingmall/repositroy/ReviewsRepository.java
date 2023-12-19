package com.project.shoppingmall.repositroy;

import com.project.shoppingmall.domain.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
}
