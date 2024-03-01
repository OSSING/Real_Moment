package com.project.Real_Moment.presentation.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ItemDto {

    @Getter
    @NoArgsConstructor
    public static class ItemResponse {
        private Long itemId;
        private String name;
        private int price;
        private int discountRate;
        private int discountPrice;
        private int sellPrice;
        private boolean isSellCheck;
        private String mainImg;

        @QueryProjection
        public ItemResponse(Long itemId, String name, int price, int discountRate, int discountPrice, int sellPrice, boolean isSellCheck, String mainImg) {
            this.itemId = itemId;
            this.name = name;
            this.price = price;
            this.discountRate = discountRate;
            this.discountPrice = discountPrice;
            this.sellPrice = sellPrice;
            this.isSellCheck = isSellCheck;
            this.mainImg = mainImg;
        }
    }
}
