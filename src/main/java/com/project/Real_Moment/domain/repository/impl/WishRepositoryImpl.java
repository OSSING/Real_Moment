package com.project.Real_Moment.domain.repository.impl;

import com.project.Real_Moment.domain.entity.Wish;
import com.project.Real_Moment.domain.repository.custom.WishRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.project.Real_Moment.domain.entity.QItem.item;
import static com.project.Real_Moment.domain.entity.QWish.wish;


@RequiredArgsConstructor
public class WishRepositoryImpl implements WishRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Wish> findWishByMemberIdPaging(Pageable pageable, Long id, int nowPage) {

        List<Wish> wishList = queryFactory
                .selectFrom(wish)
                .join(wish.itemId, item)
                .where(wish.memberId.id.eq(id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(wish.count())
                .from(wish)
                .join(wish.itemId, item)
                .where(wish.memberId.id.eq(id))
                .fetchOne();

        return new PageImpl<>(wishList, pageable, total);
    }
}
