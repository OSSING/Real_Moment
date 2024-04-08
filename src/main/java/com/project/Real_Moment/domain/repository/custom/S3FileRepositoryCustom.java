package com.project.Real_Moment.domain.repository.custom;

import com.project.Real_Moment.domain.entity.Item;
import com.project.Real_Moment.presentation.dto.ItemDto;
import com.project.Real_Moment.presentation.dto.S3FileDto;

import java.util.List;

public interface S3FileRepositoryCustom {

    List<S3FileDto.GetS3File> findMainImgList_UrlByItemId(Item item);

    String findMainImg_UrlByItemId(Item item);

    List<S3FileDto.GetS3File> findSubImgList_UrlByItemId(Item item);

    void updateImg(Long s3FileId, String fileName, String fileUrl);

}
