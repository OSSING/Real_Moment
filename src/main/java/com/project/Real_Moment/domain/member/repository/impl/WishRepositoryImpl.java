package com.project.Real_Moment.domain.member.repository.impl;

import com.project.Real_Moment.domain.member.repository.custom.WishRepositoryCustom;
import com.project.Real_Moment.presentation.dto.ItemDto;
import com.project.Real_Moment.presentation.dto.WishDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.Real_Moment.domain.member.entity.QItem.item;
import static com.project.Real_Moment.domain.member.entity.QWish.wish;


@RequiredArgsConstructor
public class WishRepositoryImpl implements WishRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<WishDto.WishListResponse> findWishByMemberId(Long id) {

        return queryFactory
                .select(wish)
                .from(wish)
                .join(wish.itemId, item)
                .where(wish.memberId.id.eq(id))
                .fetch()
                .stream()
                .map(wish -> new WishDto.WishListResponse(
                        wish.getWishId(),
                        new ItemDto.ItemResponse(
                                wish.getItemId().getId(),
                                wish.getItemId().getName(),
                                wish.getItemId().getPrice(),
                                wish.getItemId().getDiscountRate(),
                                wish.getItemId().getDiscountPrice(),
                                wish.getItemId().getSellPrice(),
                                wish.getItemId().isSellCheck(),
                                wish.getItemId().getMainImg()
                        )
                ))
                .collect(Collectors.toList());
    }
}
