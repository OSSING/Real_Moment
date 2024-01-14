package com.project.shoppingmall.repository;

import com.project.shoppingmall.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
