package com.project.Real_Moment.application.member;

import com.project.Real_Moment.domain.member.entity.Item;
import com.project.Real_Moment.domain.member.repository.ItemRepository;
import com.project.Real_Moment.presentation.dto.CondDto;
import com.project.Real_Moment.presentation.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public ItemDto.ItemCondResponse getItemList(CondDto.ItemListCond dto) {
        Pageable pageable = PageRequest.of(dto.getNowPage() - 1, 10);

        Page<Item> itemList = itemRepository.findItemListByCond(pageable, dto);

        List<ItemDto.ItemResponse> itemListDto = itemList.stream()
                .map(ItemDto.ItemResponse::new)
                .toList();

        return new ItemDto.ItemCondResponse(itemListDto, itemList.getTotalPages(), dto.getNowPage());
    }

    @Transactional(readOnly = true)
    public ItemDto.ItemDetResponse getItemDet(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        return new ItemDto.ItemDetResponse(item);
    }
}
