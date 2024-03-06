package com.project.Real_Moment.domain.member.repository.custom;

import com.project.Real_Moment.domain.member.entity.Address;
import com.project.Real_Moment.presentation.dto.AddressDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AddressRepositoryCustom {

    void updateAddress(AddressDto.AddressRequest dto);

    Page<Address> findAddressByPaging(Long id, Pageable pageable);

}
