package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartsRepository extends JpaRepository<Cart, Long> {
}
