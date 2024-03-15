package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.entity.Admin;
import com.project.Real_Moment.domain.entity.Comment;
import com.project.Real_Moment.domain.entity.OneOnOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentResponse {
        private Long commentId;
        private String content;

        public CommentResponse(Comment comment) {
            commentId = comment.getId();
            content = comment.getContent();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentRequest {
        private Long oneOnOneId;
        private String content;

        public Comment toEntity(Admin admin, OneOnOne oneOnOne) {
            return Comment.builder()
                    .adminId(admin)
                    .oneOnOneId(oneOnOne)
                    .content(content)
                    .build();
        }
    }
}
