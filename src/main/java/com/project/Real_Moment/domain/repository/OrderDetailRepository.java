package com.project.Real_Moment.domain.repository;

import com.project.Real_Moment.domain.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
