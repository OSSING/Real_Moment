package com.project.Real_Moment.domain.repository.impl;

import com.project.Real_Moment.domain.entity.Item;
import com.project.Real_Moment.domain.entity.ItemFile;
import com.project.Real_Moment.domain.entity.QItemFile;
import com.project.Real_Moment.domain.repository.custom.ItemFileRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

import static com.project.Real_Moment.domain.entity.QItemFile.itemFile;

@RequiredArgsConstructor
public class ItemFileRepositoryImpl implements ItemFileRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ItemFile> findByItemId(Item item) {
        return queryFactory
                .selectFrom(itemFile)
                .where(itemFile.itemId.eq(item))
                .fetch();
    }

    @Override
    public List<ItemFile> findByItemIdWithS3File(Long itemId) {
        return queryFactory
                .selectFrom(itemFile)
                .leftJoin(itemFile.s3FileId).fetchJoin()
                .where(itemFile.itemId.id.eq(itemId))
                .fetch();
    }

    // 요청된 number보다 크거나 같은 객체 리스트 반환
    @Override
    public List<ItemFile> findListByGoeNumber(Item findItem, int number) {
        return queryFactory
                .selectFrom(itemFile)
                .where(itemFile.itemId.eq(findItem),
                        itemFile.number.goe(number))
                .orderBy(itemFile.number.asc())
                .fetch();
    }

    // 객체의 number + 1 순서 조정
    @Override
    public void updateGoeNumberPlus(ItemFile findItemFile) {
        queryFactory
                .update(itemFile)
                .set(itemFile.number, itemFile.number.add(1))
                .where(itemFile.id.eq(findItemFile.getId()))
                .execute();
    }

    @Override
    public void updateGoeNumberMinus(ItemFile findItemFile) {
        queryFactory
                .update(itemFile)
                .set(itemFile.number, itemFile.number.subtract(1))
                .where(itemFile.id.eq(findItemFile.getId()))
                .execute();
    }

    @Override
    public void updateChangeNumber(Item findItem, int number1, int number2) {

        Long itemFileId1 = Objects.requireNonNull(queryFactory
                        .selectFrom(itemFile)
                        .where(itemFile.number.eq(number1),
                                itemFile.itemId.eq(findItem),
                                itemFile.mainOrSub.eq("main"))
                        .fetchOne())
                        .getId();

        Long itemFileId2 = Objects.requireNonNull(queryFactory
                        .selectFrom(itemFile)
                        .where(itemFile.number.eq(number2),
                                itemFile.itemId.eq(findItem),
                                itemFile.mainOrSub.eq("main"))
                        .fetchOne())
                        .getId();

        queryFactory
                .update(itemFile)
                .set(itemFile.number, number2)
                .where(itemFile.itemId.eq(findItem),
                        itemFile.mainOrSub.eq("main"),
                        itemFile.id.eq(itemFileId1))
                .execute();

        queryFactory
                .update(itemFile)
                .set(itemFile.number, number1)
                .where(itemFile.itemId.eq(findItem),
                        itemFile.mainOrSub.eq("main"),
                        itemFile.id.eq(itemFileId2))
                .execute();
    }
}
