package com.project.Real_Moment.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        private int nowPage;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OneOnOneListCond {
        private Boolean answer;
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
}
