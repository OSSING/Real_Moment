package com.project.Real_Moment.domain.repository.impl;

import com.project.Real_Moment.domain.entity.Item;
import com.project.Real_Moment.domain.entity.ItemFile;
import com.project.Real_Moment.domain.entity.QItemFile;
import com.project.Real_Moment.domain.repository.custom.ItemFileRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

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
}
