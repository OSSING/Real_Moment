package com.project.shoppingmall.repositroy;

import com.project.shoppingmall.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
