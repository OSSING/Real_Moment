package com.project.Real_Moment.domain.repository;

import com.project.Real_Moment.domain.entity.Order;
import com.project.Real_Moment.domain.repository.custom.OrderRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
    List<Order> findByMemberId_Id(Long id);

    Optional<Order> findByMerchantUid(String merchantUid);
}
