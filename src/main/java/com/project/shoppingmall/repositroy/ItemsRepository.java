package com.project.shoppingmall.repositroy;

import com.project.shoppingmall.domain.Items;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsRepository extends JpaRepository<Items, Long> {
}
