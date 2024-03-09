package com.project.Real_Moment.domain.repository;

import com.project.Real_Moment.domain.entity.Item;
import com.project.Real_Moment.domain.repository.custom.ItemRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {
}
