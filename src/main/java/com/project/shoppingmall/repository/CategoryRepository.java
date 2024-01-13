package com.project.shoppingmall.repository;

import com.project.shoppingmall.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
