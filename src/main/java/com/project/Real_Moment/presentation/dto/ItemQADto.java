package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.member.entity.Item;
import com.project.Real_Moment.domain.member.entity.ItemQA;
import com.project.Real_Moment.domain.member.entity.Member;
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
    public static class ItemQAListPage {
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

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyItemQAListPage {
        private List<ItemQADto.MyItemQAList> itemQAList;
        private long totalPage;
        private long nowPage;
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyItemQAList {
        private Long itemQAId;
        private ItemDto.ItemResponse item;
        private String loginId;
        private String title;
        private String content;
        private Boolean isAnswer;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;
        private QACommentDto.QAComment qaComment;

        public MyItemQAList(ItemQA itemQA) {
            itemQAId = itemQA.getId();
            item = new ItemDto.ItemResponse(itemQA.getItemId());
            loginId = itemQA.getMemberId().getLoginId();
            title = itemQA.getTitle();
            content = itemQA.getContent();
            isAnswer = itemQA.isAnswer();
            createdDate = itemQA.getCreatedDate();
            lastModifiedDate = itemQA.getLastModifiedDate();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SaveQARequest {
        private Long itemId;
        private String title;
        private String content;

        public ItemQA toEntity(Member member, Item item) {
            return ItemQA.builder()
                    .itemId(item)
                    .memberId(member)
                    .title(title)
                    .content(content)
                    .build();
        }
    }
}
