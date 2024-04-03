package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.entity.Category;
import com.project.Real_Moment.domain.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class ItemDto {

    @Getter @Setter
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
        private Boolean isSell;
        private Boolean isDelete;
        private List<S3FileDto.GetS3File> mainImg;
        private List<S3FileDto.GetS3File> subImg;

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
        }
    }

    @Getter @Setter
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

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AdminItemListWrapper {
        private List<AdminItemList> itemList;
        private int totalPage;
        private int nowPage;
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AdminItemList {
        private Long itemId;
        private String name;
        private int price;
        private int discountRate;
        private int discountPrice;
        private int sellPrice;
        private int stock;
        private int sellCount;
        private int totalSales; // 상품 총 매출
        private Boolean isSell;
        private String mainImg;

        public AdminItemList(Item item) {
            itemId = item.getId();
            name = item.getName();
            price = item.getPrice();
            discountRate = item.getDiscountRate();
            discountPrice = item.getDiscountPrice();
            sellPrice = item.getSellPrice();
            stock = item.getStock();
            sellCount = item.getSellCount();
            isSell = item.isSell();
        }
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AdminItemDef {
        private Long itemId;
        private String name;
        private String content;
        private int price;
        private int discountRate;
        private int discountPrice;
        private int sellPrice;
        private int stock;
        private int sellCount;
        private int totalSales; // 상품 총 매출
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;
        private Boolean isSell;
        private Boolean isDelete;
        private List<S3FileDto.GetS3File> mainImg;
        private List<S3FileDto.GetS3File> subImg;

        public AdminItemDef(Item item) {
            itemId = item.getId();
            name = item.getName();
            content = item.getContent();
            price = item.getPrice();
            discountRate = item.getDiscountRate();
            discountPrice = item.getDiscountPrice();
            sellPrice = item.getSellPrice();
            stock = item.getStock();
            sellCount = item.getSellCount();
            createdDate = item.getCreatedDate();
            lastModifiedDate = item.getLastModifiedDate();
            isSell = item.isSell();
            isDelete = item.isDelete();
        }
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SaveItem {
        private Long categoryId;
        private String name;
        private String content;
        private int price;
        private int discountRate;
        private int discountPrice;
        private int sellPrice;
        private int stock;
        private boolean sell;

        public Item toEntity(Category category) {
            return Item.builder()
                    .categoryId(category)
                    .name(name)
                    .content(content)
                    .price(price)
                    .discountRate(discountRate)
                    .discountPrice(discountPrice)
                    .sellPrice(sellPrice)
                    .stock(stock)
                    .isSell(sell)
                    .build();
        }
    }

//    @Getter
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public static class MainImgResponse {
//        private String fileUrl;
//    }
//
//    @Getter
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public static class SubImaResponse {
//        private String fileUrl;
//    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EditItemClick {
        private Long itemId;
        private Long categoryId;
        private String name;
        private String content;
        private int price;
        private int discountRate;
        private int discountPrice;
        private int sellPrice;
        private int stock;
        private Boolean isSell;
        private Boolean isDelete;
        private List<S3FileDto.GetS3File> mainImgDataList;
        private List<S3FileDto.GetS3File> subImgDataList;

        public EditItemClick(Item item) {
            itemId = item.getId();
            categoryId = item.getCategoryId().getId();
            name = item.getName();
            content = item.getContent();
            price = item.getPrice();
            discountRate = item.getDiscountRate();
            discountPrice = item.getDiscountPrice();
            sellPrice = item.getSellPrice();
            stock = item.getStock();
            isSell = item.isSell();
            isDelete = item.isDelete();
        }
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EditItem {
        private Long itemId;
        private Long categoryId;
        private String name;
        private String content;
        private int price;
        private int discountRate;
        private int discountPrice;
        private int sellPrice;
        private int stock;
        private Boolean isSell;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemIdRequestPart {
        private Long itemId;
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderedItemList {
        private Long itemId;
        private String name;
        private int price;
        private int discountRate;
        private int discountPrice;
        private int sellPrice;
        private Boolean isSell;
        private String mainImg;

        public OrderedItemList(Item item, String mainImg) {
            itemId = item.getId();
            name = item.getName();
            price = item.getPrice();
            discountRate = item.getDiscountRate();
            discountPrice = item.getDiscountPrice();
            sellPrice = item.getSellPrice();
            isSell = item.isSell();
            this.mainImg = mainImg;
        }
    }
}
