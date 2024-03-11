package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.entity.Category;
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
            categoryName = category.getName();
            parentCategory = category.getParent() != null ? category.getParent().getId() : null;
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SaveCategoryResponse {
        private String name;
        private Long parentId;

        public Category toEntity(Category parentCategory) {
            return Category.builder()
                    .name(name)
                    .parent(parentCategory)
                    .build();
        }
    }
}
