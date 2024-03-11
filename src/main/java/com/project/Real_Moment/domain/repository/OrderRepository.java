package com.project.Real_Moment.domain.repository;

import com.project.Real_Moment.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByMemberId_Id(Long id);
}
