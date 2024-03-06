package com.project.Real_Moment.domain.member.repository.custom;

import com.project.Real_Moment.domain.member.entity.ItemQA;
import com.project.Real_Moment.presentation.dto.CondDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemQARepositoryCustom {

    Page<ItemQA> findQAListByCond(Pageable pageable, CondDto.QAListCond dto);
}
