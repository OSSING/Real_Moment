package com.project.shoppingmall.repositroy;

import com.project.shoppingmall.domain.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
}
