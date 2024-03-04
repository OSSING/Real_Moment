package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.member.entity.Cart;
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

        public CartListResponse(Cart cart) {
            this.cartId = cart.getId();
            this.item = new ItemDto.ItemResponse(cart.getItemId());
            this.stock = cart.getStock();
            this.price = cart.getPrice();
        }
    }
}
