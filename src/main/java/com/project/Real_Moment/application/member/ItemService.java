package com.project.Real_Moment.application.member;

import com.project.Real_Moment.domain.entity.Item;
import com.project.Real_Moment.domain.repository.ItemRepository;
import com.project.Real_Moment.domain.repository.S3FileRepository;
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
    private final S3FileRepository s3FileRepository;

    @Transactional(readOnly = true)
    public ItemDto.ItemCondResponse getItemList(CondDto.ItemListCond dto) {
        int pageNumber = (dto.getNowPage() != null && dto.getNowPage() > 0) ? dto.getNowPage() : 1;
        Pageable pageable = PageRequest.of(pageNumber - 1, 9);

        Page<Item> itemList = itemRepository.findItemListByCond(pageable, dto);

        List<ItemDto.ItemResponse> itemListDto = itemList.stream()
                .map(ItemDto.ItemResponse::new)
                .toList();

        for (ItemDto.ItemResponse item : itemListDto) {
            Item findItem = itemRepository.findById(item.getItemId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

            // item 객체에 맞는 fileUrl을 추출
            List<ItemDto.MainImgList> mainImgUrl = s3FileRepository.findMainImg_UrlByItemId(findItem);
            item.setMainImg(mainImgUrl);
        }

        return new ItemDto.ItemCondResponse(itemListDto, itemList.getTotalPages(), pageNumber);
    }

    @Transactional(readOnly = true)
    public ItemDto.ItemDetResponse getItemDet(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        List<ItemDto.MainImgList> mainImgUrl = s3FileRepository.findMainImg_UrlByItemId(item);
        List<ItemDto.SubImaList> subImgUrl = s3FileRepository.findSubImg_UrlByItemId(item);

        ItemDto.ItemDetResponse itemDetResponse = new ItemDto.ItemDetResponse(item);
        itemDetResponse.setMainImg(mainImgUrl);
        itemDetResponse.setServeImg(subImgUrl);

        return itemDetResponse;
    }
}
