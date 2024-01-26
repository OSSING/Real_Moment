package com.project.Real_Moment.repository;

import com.project.Real_Moment.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsRepository extends JpaRepository<Item, Long> {
}
