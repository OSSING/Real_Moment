package com.project.Real_Moment.domain.repository.impl;

import com.project.Real_Moment.domain.entity.Item;
<<<<<<< HEAD
import com.project.Real_Moment.domain.repository.custom.ItemRepositoryCustom;
import com.project.Real_Moment.presentation.dto.CondDto;
=======
import com.project.Real_Moment.domain.entity.QItemFile;
import com.project.Real_Moment.domain.entity.QS3File;
import com.project.Real_Moment.domain.repository.custom.ItemRepositoryCustom;
import com.project.Real_Moment.presentation.dto.CondDto;
import com.project.Real_Moment.presentation.dto.ItemDto;
>>>>>>> gil_develop
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.project.Real_Moment.domain.entity.QItem.item;
import static com.project.Real_Moment.domain.entity.QItemFile.itemFile;
import static com.project.Real_Moment.domain.entity.QS3File.s3File;

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

        if (itemSort == null || itemSort.equals("new")) {
            return item.createdDate.desc();
        } else {
            switch (itemSort) {
                case "sale" -> {
                    return item.discountRate.desc();
                }
                case "low" -> {
                    return item.price.asc();
                }
                case "high" -> {
                    return item.price.desc();
                }
                case "sell" -> {
                    return item.sellCount.desc();
                }
            }
        }
        return null;
    }
}
