package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.entity.Admin;
import com.project.Real_Moment.domain.entity.ItemQA;
import com.project.Real_Moment.domain.entity.QAComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class QACommentDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QACommentResponse {
        private Long qaCommentId;
        private Long itemQAId;
        private String content;

        public QACommentResponse(QAComment qaComment) {
            qaCommentId = qaComment.getId();
            itemQAId = qaComment.getItemQAId().getId();
            content = qaComment.getContent();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SaveQACommentResponse {
        private Long itemQAId;
        private String content;

        public QAComment toEntity(Admin admin, ItemQA itemQA) {
            return QAComment.builder()
                    .adminId(admin)
                    .itemQAId(itemQA)
                    .content(content)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class editQAComment {
        private Long qaCommentId;
        private String content;

        public editQAComment(QAComment qaComment) {
            qaCommentId = qaComment.getId();
            content = qaComment.getContent();
        }
    }
}
