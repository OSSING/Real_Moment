package com.project.Real_Moment.domain.repository.custom;

import com.project.Real_Moment.domain.entity.Address;
import com.project.Real_Moment.domain.entity.Member;
import com.project.Real_Moment.presentation.dto.AddressDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AddressRepositoryCustom {

    void updateAddress(AddressDto.AddressRequest dto);

    Page<Address> findAddressByPaging(Long id, Pageable pageable);

}
