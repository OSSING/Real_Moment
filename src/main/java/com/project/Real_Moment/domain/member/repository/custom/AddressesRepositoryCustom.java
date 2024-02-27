package com.project.Real_Moment.domain.member.repository.custom;

import com.project.Real_Moment.domain.member.entity.Addresses;
import com.project.Real_Moment.presentation.dto.AddressesDto;

public interface AddressesRepositoryCustom {

    Addresses shivar(AddressesDto.AddressRequest dto);

}
