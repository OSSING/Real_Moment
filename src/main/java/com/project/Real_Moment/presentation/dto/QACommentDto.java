package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.member.entity.QAComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class QACommentDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QAComment {
        private Long qaCommentId;
        private Long itemQAId;
        private String content;

        public QAComment(com.project.Real_Moment.domain.member.entity.QAComment qaComment) {
            qaCommentId = qaComment.getId();
            itemQAId = qaComment.getItemQAId().getId();
            content = qaComment.getContent();
        }
    }
}
