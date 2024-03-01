package com.project.Real_Moment.domain.member.repository;

import com.project.Real_Moment.domain.member.entity.Address;
import com.project.Real_Moment.domain.member.repository.custom.AddressRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long>, AddressRepositoryCustom {

    List<Address> findAddressByMemberId_MemberId(Long memberId);

}
