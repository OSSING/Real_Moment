package com.project.Real_Moment.domain.repository.impl;

import com.project.Real_Moment.domain.entity.Item;
import com.project.Real_Moment.domain.repository.custom.S3FileRepositoryCustom;
import com.project.Real_Moment.presentation.dto.ItemDto;
import com.project.Real_Moment.presentation.dto.S3FileDto;
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
    public List<S3FileDto.GetS3File> findMainImgList_UrlByItemId(Item findItem) {
        return queryFactory
                .selectFrom(s3File)
                .leftJoin(itemFile).on(s3File.eq(itemFile.s3FileId))
                .leftJoin(item).on(itemFile.itemId.eq(item))
                .where(item.eq(findItem),
                        itemFile.mainOrSub.eq("main"))
                .orderBy(itemFile.number.asc())
                .fetch()
                .stream()
                .map(S3FileDto.GetS3File::new)
                .toList();
    }

    // 우선순위가 0인 대표 이미지를 반환
    @Override
    public String findMainImg_UrlByItemId(Item findItem) {
        return queryFactory
                .select(s3File.fileUrl)
                .from(s3File)
                .leftJoin(itemFile).on(s3File.eq(itemFile.s3FileId))
//                .leftJoin(item).on(itemFile.itemId.eq(item))
                .where(itemFile.itemId.eq(findItem),
                        itemFile.mainOrSub.eq("main"),
                        itemFile.number.eq(0))
                .fetchOne();
    }

    @Override
    public List<S3FileDto.GetS3File> findSubImgList_UrlByItemId(Item findItem) {
        return queryFactory
                .selectFrom(s3File)
                .leftJoin(itemFile).on(s3File.eq(itemFile.s3FileId))
                .leftJoin(item).on(itemFile.itemId.eq(item))
                .where(item.eq(findItem),
                        itemFile.mainOrSub.eq("sub"))
                .orderBy(itemFile.number.asc())
                .fetch()
                .stream()
                .map(S3FileDto.GetS3File::new)
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
