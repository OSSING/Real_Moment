package com.project.Real_Moment.domain.member.repository.impl;

import com.project.Real_Moment.domain.member.repository.custom.CartRepositoryCustom;
import com.project.Real_Moment.presentation.dto.CartDto;
import com.project.Real_Moment.presentation.dto.ItemDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.Real_Moment.domain.member.entity.QCart.cart;
import static com.project.Real_Moment.domain.member.entity.QItem.item;

@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepositoryCustom {

    private final JPAQueryFactory queryFactory;

//    @Override
//    public List<CartDto.CartListResponse> findCartListByMemberId(long id) {
//        return queryFactory
//                .select(cart)
//                .from(cart)
//                .join(cart.itemId, item)
//                .where(cart.memberId.id.eq(id))
//                .fetch()
//                .stream()
//                .map(cart -> new CartDto.CartListResponse(
//                        cart.getId(),
//                        new ItemDto.ItemResponse(
//                                cart.getItemId().getId(),
//                                cart.getItemId().getName(),
//                                cart.getItemId().getPrice(),
//                                cart.getItemId().getDiscountRate(),
//                                cart.getItemId().getDiscountPrice(),
//                                cart.getItemId().getSellPrice(),
//                                cart.getItemId().isSellCheck(),
//                                cart.getItemId().getMainImg()
//                        ),
//                        cart.getStock(),
//                        cart.getPrice()
//                ))
//                .collect(Collectors.toList());
//    }
}
