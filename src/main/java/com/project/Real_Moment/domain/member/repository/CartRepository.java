package com.project.Real_Moment.domain.member.repository;

import com.project.Real_Moment.domain.member.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
