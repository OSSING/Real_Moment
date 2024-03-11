package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.entity.Item;
import com.project.Real_Moment.domain.entity.Member;
import com.project.Real_Moment.domain.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReviewListResponse {
        private Long reviewId;
        private String loginId;
        private String title;
        private String content;
        private int star;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemDetReviewResponse {
        private List<ReviewListResponse> reviewList;
        private long totalPage;
        private long nowPage;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyReview {
        private Long reviewId;
        private ItemDto.ItemResponse item;
        private String loginId;
        private String title;
        private String content;
        private int star;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyReviewListResponse {
        private List<ReviewDto.MyReview> reviewList;
        private long totalPage;
        private long nowPage;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SaveReviewRequest {
        private Long orderId;
        private Long itemId;
        private String title;
        private String content;
        private int star;

        public Review toEntity(Member member, Item item) {
            return Review.builder()
                    .memberId(member)
                    .itemId(item)
                    .title(title)
                    .content(content)
                    .star(star)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class editReviewClick {
        private Long reviewId;
        private String title;
        private String content;
        private int star;

        public editReviewClick(Review review) {
            reviewId = review.getId();
            title = review.getTitle();
            content = review.getContent();
            star = review.getStar();
        }
    }
}
