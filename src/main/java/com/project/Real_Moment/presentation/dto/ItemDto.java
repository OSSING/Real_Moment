package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.member.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ItemDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemResponse {
        private Long itemId;
        private String name;
        private int price;
        private int discountRate;
        private int discountPrice;
        private int sellPrice;
        private boolean isSell;
        private String mainImg;

        public ItemResponse(Item item) {
            this.itemId = item.getId();
            this.name = item.getName();
            this.price = item.getPrice();
            this.discountRate = item.getDiscountRate();
            this.discountPrice = item.getDiscountPrice();
            this.sellPrice = item.getSellPrice();
            this.isSell = item.isSell();
            this.mainImg = item.getMainImg();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemCondRequest {
        private String itemSort;
        private Long categoryId;
        private String itemName;
        private Boolean isDelete;
        private int nowPage;

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemCondResponse {
        private List<ItemDto.ItemResponse> item;
        private long totalPage;
        private long nowPage;
    }
}
