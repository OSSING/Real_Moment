package com.project.Real_Moment.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AdminDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CheckIdDuplicate {
        private String loginId;
    }
}
