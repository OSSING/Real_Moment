package com.project.Real_Moment.domain.member.repository.impl;

import com.project.Real_Moment.domain.member.repository.custom.CartRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.project.Real_Moment.domain.member.entity.QCart.cart;

@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void updateByStock(Long cartId, int stock) {
        queryFactory
                .update(cart)
                .set(cart.stock, stock)
                .where(cart.id.eq(cartId))
                .execute();
    }
}
