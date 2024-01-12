package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsRepository extends JpaRepository<Item, Long> {
}
