package com.project.Real_Moment.domain.repository;

import com.project.Real_Moment.domain.entity.Item;
import com.project.Real_Moment.domain.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findByItemId(Item itemId);
}
