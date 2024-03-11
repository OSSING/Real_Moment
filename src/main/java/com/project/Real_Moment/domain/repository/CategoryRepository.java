package com.project.Real_Moment.domain.repository;

import com.project.Real_Moment.domain.entity.Category;
import com.project.Real_Moment.domain.repository.custom.CategoryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {

    Optional<Category> findByParent_Id(Long parentId);
}
