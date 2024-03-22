package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.entity.S3File;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class S3FileDto {

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SaveS3File {
        private String fileName;
        private String fileUrl;

        public S3File toEntity() {
            return S3File.builder()
                    .fileName(fileName)
                    .fileUrl(fileUrl)
                    .build();
        }
    }
}
