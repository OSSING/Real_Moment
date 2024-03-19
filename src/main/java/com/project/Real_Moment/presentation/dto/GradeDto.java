package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.entity.Grade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class GradeDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GradeResponse {
        private Long gradeId;
        private String gradeName;
        private int gradePrice;
        private int rewardRate;

        public GradeResponse(Grade grade) {
            gradeId = grade.getId();
            gradeName = grade.getGradeName();
            gradePrice = grade.getGradePrice();
            rewardRate = grade.getRewardRate();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SaveGrade {
        private String gradeName;
        private int gradePrice;
        private int rewardRate;

        public Grade toEntity() {
            return Grade.builder()
                    .gradeName(gradeName)
                    .gradePrice(gradePrice)
                    .rewardRate(rewardRate)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EditGrade {
        private Long gradeId;
        private String gradeName;
        private int rewardRate;
    }
}
