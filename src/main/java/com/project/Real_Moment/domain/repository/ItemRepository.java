package com.project.Real_Moment.domain.repository;

import com.project.Real_Moment.domain.entity.Category;
import com.project.Real_Moment.domain.entity.Item;
import com.project.Real_Moment.domain.repository.custom.ItemRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {
    List<Item> findByCategoryId(Category category);
}
