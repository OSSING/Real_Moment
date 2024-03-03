package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.member.entity.Category;
import lombok.*;

public class CategoryDto {

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CategoryListRes {
        private Long categoryId;
        private String categoryName;
        private Long parentCategory;

        public CategoryListRes(Category category) {
            categoryId = category.getId();
            categoryName = category.getCategoryName();
            parentCategory = category.getParentCategory() != null ? category.getParentCategory().getId() : null;
        }
    }
}
