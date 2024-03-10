package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.entity.OneOnOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class OneOnOneDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OneOnOneWrapper {
        private List<OneOnOneDto.OneOnOneList> oneOnOneList;
        private long totalPage;
        private long nowPage;
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OneOnOneList {
        private Long oneOnOneId;
        private String title;
        private String content;
        private CommentDto.commentResponse comment;

        public OneOnOneList(OneOnOne oneOnOne) {
            oneOnOneId = oneOnOne.getId();
            title = oneOnOne.getTitle();
            content = oneOnOne.getContent();
        }
    }
}
