package com.project.Real_Moment.domain.member.repository.custom;

import com.project.Real_Moment.domain.member.entity.Address;
import com.project.Real_Moment.presentation.dto.AddressDto;

public interface AddressRepositoryCustom {

    void updateAddress(AddressDto.AddressRequest dto);

}