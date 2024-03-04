package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.member.entity.Cart;
import com.project.Real_Moment.domain.member.entity.Item;
import com.project.Real_Moment.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SaveCartRequest {
        private Long itemId;
        private int stock;

        public Cart toEntity(Member member, Item item, int stock) {
            return Cart.builder()
                    .memberId(member)
                    .itemId(item)
                    .stock(stock)
                    .price(item.getPrice())
                    .build();
        }
    }
}
