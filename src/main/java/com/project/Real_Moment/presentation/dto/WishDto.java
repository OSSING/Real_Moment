package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.member.entity.Item;
import com.project.Real_Moment.domain.member.entity.Member;
import com.project.Real_Moment.domain.member.entity.Wish;
import lombok.*;

public class WishDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WishListResponse {
        private Long wishId;
        private ItemDto.ItemResponse item;
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
