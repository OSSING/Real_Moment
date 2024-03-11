package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.entity.Category;
import lombok.*;

public class CategoryDto {

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CategoryList {
        private Long categoryId;
        private String name;
        private Long parentId;

        public CategoryList(Category category) {
            categoryId = category.getId();
            name = category.getName();
            parentId = category.getParent() != null ? category.getParent().getId() : null;
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
