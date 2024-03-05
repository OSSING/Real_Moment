package com.project.Real_Moment.domain.member.repository.impl;

import com.project.Real_Moment.domain.member.entity.Item;
import com.project.Real_Moment.domain.member.repository.custom.ItemRepositoryCustom;
import com.project.Real_Moment.presentation.dto.ItemDto;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.Real_Moment.domain.member.entity.QItem.item;

@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public ItemDto.ItemCondResponse findItemListByCond(String itemSort, Long categoryId, String itemName, Boolean isDelete, int nowPage) {
        Pageable pageable = PageRequest.of(nowPage - 1, 10);
        JPAQuery<Item> query = queryFactory.selectFrom(item);

        if (categoryId != null) {
            query.where(item.categoryId.id.eq(categoryId));
        }

        if (itemName != null) {
            query.where(item.name.eq(itemName));
        }

        if (isDelete != null) {
            query.where(item.isDelete.eq(isDelete));
        }

        if (itemSort != null) {
            if (itemSort.equals("new")) {
                query.orderBy(item.createdDate.asc());
            } else if (itemSort.equals("sale")) {
                query.orderBy(item.discountRate.desc());
            }
        }

        QueryResults<Item> results = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ItemDto.ItemResponse> itemList = results.getResults().stream()
                .map(item -> new ItemDto.ItemResponse(
                        item.getId(),
                        item.getName(),
                        item.getPrice(),
                        item.getDiscountRate(),
                        item.getDiscountPrice(),
                        item.getSellPrice(),
                        item.isSell(),
                        item.getMainImg()
                ))
                .collect(Collectors.toList());

        int totalPages = (int) Math.ceil((double) results.getTotal() / pageable.getPageSize());
        return new ItemDto.ItemCondResponse(itemList, totalPages, nowPage);

    }
}
