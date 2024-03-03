package com.project.Real_Moment.domain.member.repository;

import com.project.Real_Moment.domain.member.entity.Cart;
import com.project.Real_Moment.domain.member.repository.custom.CartRepositoryCustom;
import com.project.Real_Moment.presentation.dto.CartDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long>, CartRepositoryCustom {
}
