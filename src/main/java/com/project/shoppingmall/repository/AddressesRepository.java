package com.project.shoppingmall.repository;

import com.project.shoppingmall.entity.Addresses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressesRepository extends JpaRepository<Addresses, Long> {
}
