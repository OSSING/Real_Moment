package com.project.Real_Moment.domain.repository;

import com.project.Real_Moment.domain.entity.Address;
import com.project.Real_Moment.domain.entity.Member;
import com.project.Real_Moment.domain.repository.custom.AddressRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long>, AddressRepositoryCustom {


    Address findByMemberIdAndIsDefAddressIsTrue(Member member);
}
