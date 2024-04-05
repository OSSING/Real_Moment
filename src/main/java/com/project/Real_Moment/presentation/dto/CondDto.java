package com.project.Real_Moment.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CondDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QAListCond {
        private Long itemId;
        private Boolean isAnswer;
        private int nowPage;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemListCond {
        private String itemSort;
        private Long categoryId;
        private String itemName;
        private Boolean isDelete;
        private Integer nowPage;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OneOnOneListCond {
        private Boolean isAnswer;
        private int nowPage;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReviewListCond {
        private Long itemId;
        private Integer star;
        private int nowPage;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AdminOneOnOneListCond {
        private String loginId;
        private Boolean isAnswer;
        private int nowPage;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberListCond {
        private String memberSort;
        private String loginId;
        private Long gradeId;
        private Boolean isDelete;
        private int nowPage;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AdminListCond {
        private String loginId;
        private String name;
        private String roles;
        private Integer nowPage;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberOrderListCond {
        private String itemName;
        private LocalDate startDate;
        private LocalDate lastDate;
        private String status;
        private int nowPage;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AdminOrderListCond {
        private String itemName;
        private String loginId;
        private String merchantUid;
        private LocalDateTime startDate;
        private LocalDateTime lastDate;
        private String status;
        private int nowPage;
    }
}
