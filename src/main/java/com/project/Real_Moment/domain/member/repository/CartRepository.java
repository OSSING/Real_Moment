package com.project.Real_Moment.domain.member.repository;

import com.project.Real_Moment.domain.member.entity.Cart;
import com.project.Real_Moment.domain.member.entity.Item;
import com.project.Real_Moment.domain.member.entity.Member;
import com.project.Real_Moment.domain.member.repository.custom.CartRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long>, CartRepositoryCustom {

    List<Cart> findByMemberId_Id(long id);

    boolean existsByItemIdAndMemberId(Item item, Member member);
}
