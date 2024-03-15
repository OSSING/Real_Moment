package com.project.Real_Moment.domain.repository.custom;

import com.project.Real_Moment.domain.entity.OneOnOne;
import com.project.Real_Moment.presentation.dto.CondDto;
import com.project.Real_Moment.presentation.dto.OneOnOneDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OneOnOneRepositoryCustom {
    Page<OneOnOne> findOneOnOneListByPaging(Long memberId, CondDto.OneOnOneListCond dto, Pageable pageable);

    void updateOneOnOne(Long memberId, OneOnOneDto.editOneOnOneClick dto);

    Page<OneOnOne> findOneOnOneListByPaging_admin(Pageable pageable, CondDto.AdminOneOnOneListCond dto);

    void updateOneOnOneByComment(Long oneOnOneId);

    void updateOneOnOneById(Long oneOnOneId);
}
