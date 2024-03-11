package com.project.Real_Moment.domain.repository.impl;

import com.project.Real_Moment.domain.entity.Item;
import com.project.Real_Moment.domain.repository.custom.ItemRepositoryCustom;
import com.project.Real_Moment.presentation.dto.CondDto;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.project.Real_Moment.domain.entity.QItem.item;

@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Item> findItemListByCond(Pageable pageable, CondDto.ItemListCond dto) {

        List<Item> itemList = queryFactory
                .selectFrom(item)
                .where(categoryIdEq(dto.getCategoryId()),
                        itemNameEq(dto.getItemName()),
                        isDeleteEq(dto.getIsDelete()))
                .orderBy(itemSortEq(dto.getItemSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(item.count())
                .from(item)
                .where(categoryIdEq(dto.getCategoryId()),
                        itemNameEq(dto.getItemName()),
                        isDeleteEq(dto.getIsDelete()))
                .fetchOne();

        return new PageImpl<>(itemList, pageable, total);

//        if (categoryId != null) {
//            query.where(item.categoryId.id.eq(categoryId));
//            countQuery.where(item.categoryId.id.eq(categoryId));
//        }
//
//        if (itemName != null) {
//            query.where(item.name.eq(itemName));
//            countQuery.where(item.name.eq(itemName));
//        }
//
//        if (isDelete != null) {
//            query.where(item.isDelete.eq(isDelete));
//            countQuery.where(item.isDelete.eq(isDelete));
//        }

//        if (itemSort != null) {
//            if (itemSort.equals("new")) {
//                query.orderBy(item.createdDate.asc());
//            } else if (itemSort.equals("sale")) {
//                query.orderBy(item.discountRate.desc());
//            }
//        }

//        List<ItemDto.ItemResponse> results = query
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch()
//                .stream()
//                .map(item -> new ItemDto.ItemResponse(
//                        item.getId(),
//                        item.getName(),
//                        item.getPrice(),
//                        item.getDiscountRate(),
//                        item.getDiscountPrice(),
//                        item.getSellPrice(),
//                        item.isSell(),
//                        item.getMainImg()
//                ))
//                .toList();
//
//        Long total = countQuery.fetchOne();

//        int totalPages = (int) Math.ceil((double) total / pageable.getPageSize());
//        return new ItemDto.ItemCondResponse(results, totalPages, nowPage);

    }

    private BooleanExpression categoryIdEq(Long categoryId) {
        return categoryId != null ? item.categoryId.id.eq(categoryId) : null;
    }

    private BooleanExpression itemNameEq(String itemName) {
        return itemName != null ? item.name.eq(itemName) : null;
    }

    private BooleanExpression isDeleteEq(Boolean isDelete) {
        return isDelete != null ? item.isDelete.eq(isDelete) : null;
    }

    private OrderSpecifier<?> itemSortEq(String itemSort) {
        if (itemSort != null) {
            if (itemSort.equals("new")) {
                return item.createdDate.asc();
            } else if (itemSort.equals("sale")) {
                return item.discountRate.desc();
            }
        }
        return null;
    }
}
