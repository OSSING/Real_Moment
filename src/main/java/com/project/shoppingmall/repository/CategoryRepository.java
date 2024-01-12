package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
