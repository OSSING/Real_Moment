package com.project.Real_Moment.domain.member.repository.impl;

import com.project.Real_Moment.domain.member.entity.Wish;
import com.project.Real_Moment.domain.member.repository.custom.WishRepositoryCustom;
import com.project.Real_Moment.presentation.dto.ItemDto;
import com.project.Real_Moment.presentation.dto.WishDto;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.Real_Moment.domain.member.entity.QItem.item;
import static com.project.Real_Moment.domain.member.entity.QWish.wish;


@RequiredArgsConstructor
public class WishRepositoryImpl implements WishRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public WishDto.WishListResponseWrapper findWishByMemberId(Long id, int nowPage, int size) {

        Pageable pageable = PageRequest.of(nowPage - 1, size);

        QueryResults<Wish> results = queryFactory
                .selectFrom(wish)
                .join(wish.itemId, item)
                .where(wish.memberId.id.eq(id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<WishDto.WishListResponse> wishList = results.getResults().stream()
                .map(wish -> new WishDto.WishListResponse(
                        wish.getId(),
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
                .toList();

        return new WishDto.WishListResponseWrapper(wishList, results.getTotal(), nowPage);

    }
}
