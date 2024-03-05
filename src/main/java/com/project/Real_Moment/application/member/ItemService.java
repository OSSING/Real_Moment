package com.project.Real_Moment.application.member;

import com.project.Real_Moment.domain.member.entity.Item;
import com.project.Real_Moment.domain.member.repository.ItemRepository;
import com.project.Real_Moment.presentation.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public ItemDto.ItemCondResponse getItemList(String itemSort, Long categoryId, String itemName, Boolean isDelete, int nowPage) {
        return itemRepository.findItemListByCond(itemSort, categoryId, itemName, isDelete, nowPage);
    }

    @Transactional(readOnly = true)
    public ItemDto.ItemDetResponse getItemDet(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        return new ItemDto.ItemDetResponse(item);
    }
}
