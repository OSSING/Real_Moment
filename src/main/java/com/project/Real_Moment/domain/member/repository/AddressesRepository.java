package com.project.Real_Moment.domain.member.repository;

import com.project.Real_Moment.domain.member.entity.Addresses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressesRepository extends JpaRepository<Addresses, Long> {
}
