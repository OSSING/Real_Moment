package com.project.Real_Moment.domain.repository.custom;

import com.project.Real_Moment.presentation.dto.GradeDto;

public interface GradeRepositoryCustom {
    void updateGrade(GradeDto.EditGrade dto);
}
