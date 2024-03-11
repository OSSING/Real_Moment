package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class commentResponse {
        private Long commentId;
        private String content;

        public commentResponse(Comment comment) {
            commentId = comment.getId();
            content = comment.getContent();
        }
    }
}
