package com.project.Real_Moment.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
}
