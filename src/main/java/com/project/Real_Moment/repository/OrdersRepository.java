package com.project.Real_Moment.repository;

import com.project.Real_Moment.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
