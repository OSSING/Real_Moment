package com.project.Real_Moment.domain.repository.custom;

import com.project.Real_Moment.domain.entity.Item;
import com.project.Real_Moment.presentation.dto.CondDto;

import com.project.Real_Moment.presentation.dto.ItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemRepositoryCustom {

    Page<Item> findItemListByCond(Pageable pageable, CondDto.ItemListCond dto);

}
