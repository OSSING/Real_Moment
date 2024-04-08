package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.entity.S3File;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetS3File {
        private Long s3FileId;
        private String fileName;
        private String fileUrl;

        public GetS3File(S3File s3File) {
            s3FileId = s3File.getId();
            fileName = s3File.getFileName();
            fileUrl = s3File.getFileUrl();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class s3FileIdRequestPart {
        private List<Long> s3FileId;
    }
}
