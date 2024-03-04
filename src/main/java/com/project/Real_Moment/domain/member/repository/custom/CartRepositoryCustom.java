package com.project.Real_Moment.domain.member.repository.custom;

import com.project.Real_Moment.presentation.dto.CartDto;

import java.util.List;

public interface CartRepositoryCustom {

    void updateByStock(Long cartId, int stock);

}
