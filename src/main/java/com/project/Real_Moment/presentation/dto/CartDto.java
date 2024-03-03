package com.project.Real_Moment.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CartDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CartListResponse {

        private Long cartId;
        private ItemDto.ItemResponse item;
        private int stock;
        private int price;
        private boolean isCheck;
    }
}
