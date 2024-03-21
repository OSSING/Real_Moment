package com.project.Real_Moment.application.admin;

import com.project.Real_Moment.domain.entity.Item;
import com.project.Real_Moment.domain.entity.OrderDetail;
import com.project.Real_Moment.domain.repository.ItemFileRepository;
import com.project.Real_Moment.domain.repository.ItemRepository;
import com.project.Real_Moment.domain.repository.OrderDetailRepository;
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
public class AdminItemService {

    private final ItemRepository itemRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final S3FileRepository s3FileRepository;

    @Transactional(readOnly = true)
    public ItemDto.AdminItemListWrapper getItemList(CondDto.ItemListCond dto) {
        int pageNumber = (dto.getNowPage() != null && dto.getNowPage() > 0) ? dto.getNowPage() : 1;
        Pageable pageable = PageRequest.of(pageNumber - 1, 9);

        Page<Item> itemListPaging = itemRepository.findItemListByCond(pageable, dto);

        List<ItemDto.AdminItemList> itemList = itemListPaging.stream()
                .map(ItemDto.AdminItemList::new)
                .toList();

        for (ItemDto.AdminItemList item : itemList) {
            Item findItem = itemRepository.findById(item.getItemId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

            int totalSales = getTotalSales(findItem);

            item.setTotalSales(totalSales);

            // item 객체에 맞는 fileUrl을 추출
            List<ItemDto.MainImgList> fileUrl = s3FileRepository.findMainImg_UrlByItemId(findItem);
            item.setMainImg(fileUrl);
        }

        return new ItemDto.AdminItemListWrapper(itemList, itemListPaging.getTotalPages(), pageNumber);
    }

    @Transactional(readOnly = true)
    public ItemDto.AdminItemDef getItemDef(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        ItemDto.AdminItemDef itemDefDto = new ItemDto.AdminItemDef(item);

        int totalSales = getTotalSales(item);
        itemDefDto.setTotalSales(totalSales);

        List<ItemDto.MainImgList> mainImgUrl = s3FileRepository.findMainImg_UrlByItemId(item);
        itemDefDto.setMainImg(mainImgUrl);

        List<ItemDto.SubImaList> subImgUrl = s3FileRepository.findSubImg_UrlByItemId(item);
        itemDefDto.setSubImg(subImgUrl);

        return itemDefDto;
    }

    // orderDetail에 저장된 데이터로 item의 총 매출을 계산

    private int getTotalSales(Item item) {
        List<OrderDetail> OrderedItem = orderDetailRepository.findByItemId(item);

        int totalSales = 0;
        for (OrderDetail orderDetail : OrderedItem) {
            totalSales += orderDetail.getTotalPrice();
        }

        return totalSales;
    }
}
