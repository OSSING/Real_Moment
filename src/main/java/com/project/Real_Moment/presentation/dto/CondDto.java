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
}
