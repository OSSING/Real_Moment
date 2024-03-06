package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.member.entity.ItemQA;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class ItemQADto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemQAListResponse {
        private List<ItemQADto.ItemQAList> itemQAList;
        private long totalPage;
        private long nowPage;
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemQAList {
        private Long itemQAId;
        private String loginId;
        private String title;
        private String content;
        private Boolean isAnswer;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;
        private QACommentDto.QAComment qaComment;

        public ItemQAList(ItemQA itemQA) {
            itemQAId = itemQA.getId();
            loginId = itemQA.getMemberId().getLoginId();
            title = itemQA.getTitle();
            content = itemQA.getContent();
            isAnswer = itemQA.isAnswer();
            createdDate = itemQA.getCreatedDate();
            lastModifiedDate = itemQA.getLastModifiedDate();
        }
    }
}
