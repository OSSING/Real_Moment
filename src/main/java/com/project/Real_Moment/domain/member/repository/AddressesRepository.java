package com.project.Real_Moment.domain.member.repository;

import com.project.Real_Moment.domain.member.entity.Addresses;
import com.project.Real_Moment.domain.member.repository.custom.AddressRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressesRepository extends JpaRepository<Addresses, Long>, AddressRepositoryCustom {

    List<Addresses> findAddressesByMemberId_MemberId(Long memberId);
}
