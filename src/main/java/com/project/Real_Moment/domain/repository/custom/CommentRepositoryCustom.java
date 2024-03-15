package com.project.Real_Moment.domain.repository.custom;

import com.project.Real_Moment.domain.entity.Admin;
import com.project.Real_Moment.presentation.dto.CommentDto;

public interface CommentRepositoryCustom {

    void updateComment(CommentDto.CommentResponse dto);
}
