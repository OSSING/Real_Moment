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
    public ItemDto.ItemCondResponse findItemListByCond(ItemDto.ItemCondRequest dto) {
        Pageable pageable = PageRequest.of(dto.getNowPage() - 1, 10);
        JPAQuery<Item> query = queryFactory.selectFrom(item);

        if (dto.getCategoryId() != null) {
            query.where(item.categoryId.id.eq(dto.getCategoryId()));
        }

        if (dto.getItemName() != null) {
            query.where(item.name.eq(dto.getItemName()));
        }

        if (dto.getIsDelete() != null) {
            query.where(item.isDelete.eq(dto.getIsDelete()));
        }

        if (dto.getItemSort() != null) {
            if (dto.getItemSort().equals("new")) {
                query.orderBy(item.createdDate.asc());
            } else if (dto.getItemSort().equals("sale")) {
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

        return new ItemDto.ItemCondResponse(itemList, results.getTotal(), dto.getNowPage());

    }
}
