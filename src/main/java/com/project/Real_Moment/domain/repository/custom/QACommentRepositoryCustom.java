package com.project.Real_Moment.domain.repository.custom;

import com.project.Real_Moment.presentation.dto.QACommentDto;
import lombok.RequiredArgsConstructor;

public interface QACommentRepositoryCustom {
    void updateById(QACommentDto.EditQAComment dto);
}
