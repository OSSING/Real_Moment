package com.project.shoppingmall.repositroy;

import com.project.shoppingmall.domain.Carts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartsRepository extends JpaRepository<Carts, Long> {
}
