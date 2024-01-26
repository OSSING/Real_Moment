package com.project.Real_Moment.repository;

import com.project.Real_Moment.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartsRepository extends JpaRepository<Cart, Long> {
}
