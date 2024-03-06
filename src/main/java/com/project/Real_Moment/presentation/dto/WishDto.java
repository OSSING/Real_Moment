package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.member.entity.Item;
import com.project.Real_Moment.domain.member.entity.Member;
import com.project.Real_Moment.domain.member.entity.Wish;
import lombok.*;

import java.util.List;

public class WishDto {

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WishListResponse {
        private Long wishId;
        private ItemDto.ItemResponse item;

        public WishListResponse(Wish wish) {
            this.wishId = wish.getId();
            this.item = new ItemDto.ItemResponse(wish.getItemId());
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WishListResponseWrapper {
        private List<WishListResponse> wishList;
        private long totalPage;
        private long nowPage;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class saveWish {
        private Long itemId;

        public Wish toEntity(Member member, Item item) {
            return Wish.builder()
                    .memberId(member)
                    .itemId(item)
                    .build();
        }
    }
}
