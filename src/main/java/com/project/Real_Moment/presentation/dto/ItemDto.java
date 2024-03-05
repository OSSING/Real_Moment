package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.member.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class ItemDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemDetResponse {
        private Long itemId;
        private String name;
        private String content;
        private int price;
        private int discountRate;
        private int discountPrice;
        private int sellPrice;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;
        private int stock;
        private boolean isSell;
        private boolean isDelete;
        private String mainImg;
        private String serveImg;

        public ItemDetResponse(Item item) {
            itemId = item.getId();
            name = item.getName();
            content = item.getContent();
            price = item.getPrice();
            discountRate = item.getDiscountRate();
            discountPrice = item.getDiscountPrice();
            sellPrice = item.getSellPrice();
            createdDate = item.getCreatedDate();
            lastModifiedDate = item.getLastModifiedDate();
            stock = item.getStock();
            isSell = item.isSell();
            isDelete = item.isDelete();
            mainImg = item.getMainImg();
            serveImg = item.getServeImg();
        }
    }

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
        private Boolean isSell;
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
    public static class ItemCondResponse {
        private List<ItemDto.ItemResponse> item;
        private long totalPage;
        private long nowPage;
    }
}
