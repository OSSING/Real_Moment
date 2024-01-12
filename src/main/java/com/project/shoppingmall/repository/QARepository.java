package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.ItemQA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QARepository extends JpaRepository<ItemQA, Long> {
}
