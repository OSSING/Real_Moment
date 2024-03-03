package com.project.Real_Moment.domain.member.repository;

import com.project.Real_Moment.domain.member.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
