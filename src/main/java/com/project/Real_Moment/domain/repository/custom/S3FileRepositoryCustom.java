package com.project.Real_Moment.domain.repository.custom;

import com.project.Real_Moment.domain.entity.Item;
import com.project.Real_Moment.presentation.dto.ItemDto;

import java.util.List;

public interface S3FileRepositoryCustom {

    List<ItemDto.MainImgListResponse> findMainImg_UrlByItemId(Item item);

    List<ItemDto.SubImaListResponse> findSubImg_UrlByItemId(Item item);
}
