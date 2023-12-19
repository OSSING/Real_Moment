package com.project.shoppingmall.repositroy;

import com.project.shoppingmall.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
