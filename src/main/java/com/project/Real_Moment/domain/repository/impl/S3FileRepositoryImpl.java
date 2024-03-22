package com.project.Real_Moment.domain.repository.impl;

import com.project.Real_Moment.domain.entity.Item;
import com.project.Real_Moment.domain.entity.QS3File;
import com.project.Real_Moment.domain.repository.custom.S3FileRepositoryCustom;
import com.project.Real_Moment.presentation.dto.ItemDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.Real_Moment.domain.entity.QItem.item;
import static com.project.Real_Moment.domain.entity.QItemFile.itemFile;
import static com.project.Real_Moment.domain.entity.QS3File.s3File;

@RequiredArgsConstructor
public class S3FileRepositoryImpl implements S3FileRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ItemDto.MainImgListResponse> findMainImg_UrlByItemId(Item findItem) {
        return queryFactory
                .select(s3File.fileUrl)
                .from(s3File)
                .leftJoin(itemFile).on(s3File.eq(itemFile.s3FileId))
                .leftJoin(item).on(itemFile.itemId.eq(item))
                .where(item.eq(findItem),
                        itemFile.mainOrServe.eq("main"))
                .fetch()
                .stream()
                .map(ItemDto.MainImgListResponse::new)
                .toList();
    }

    @Override
    public List<ItemDto.SubImaListResponse> findSubImg_UrlByItemId(Item findItem) {
        return queryFactory
                .select(s3File.fileUrl)
                .from(s3File)
                .leftJoin(itemFile).on(s3File.eq(itemFile.s3FileId))
                .leftJoin(item).on(itemFile.itemId.eq(item))
                .where(item.eq(findItem),
                        itemFile.mainOrServe.eq("serve"))
                .fetch()
                .stream()
                .map(ItemDto.SubImaListResponse::new)
                .toList();
    }

    @Override
    public void updateImg(Long s3FileId, String fileName, String fileUrl) {
        queryFactory
                .update(s3File)
                .set(s3File.fileName, fileName)
                .set(s3File.fileUrl, fileUrl)
                .where(s3File.id.eq(s3FileId))
                .execute();
    }
}
