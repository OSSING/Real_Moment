package com.project.Real_Moment.application.member;

import com.project.Real_Moment.domain.member.repository.ItemRepository;
import com.project.Real_Moment.presentation.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemDto.ItemCondResponse getItemList(ItemDto.ItemCondRequest dto) {
        return itemRepository.findItemListByCond(dto);
    }
}
