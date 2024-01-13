package com.project.shoppingmall.repository;

import com.project.shoppingmall.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetail, Long> {
}
