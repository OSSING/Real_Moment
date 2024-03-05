package com.project.Real_Moment.domain.member.repository.custom;

import com.project.Real_Moment.presentation.dto.ItemDto;

public interface ItemRepositoryCustom {

    ItemDto.ItemCondResponse findItemListByCond(String itemSort, Long categoryId, String itemName, Boolean isDelete, int nowPage);
}
