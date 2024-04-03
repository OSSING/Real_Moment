package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.entity.Item;
import com.project.Real_Moment.domain.entity.ItemFile;
import com.project.Real_Moment.domain.entity.S3File;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ItemFileDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SaveItemFile {
        private S3File s3FileId;
        private Item itemId;
        private String mainOrSub;

        public ItemFile toEntity() {
            return ItemFile.builder()
                    .s3FileId(s3FileId)
                    .itemId(itemId)
                    .mainOrSub(mainOrSub)
                    .build();
        }
    }
}
