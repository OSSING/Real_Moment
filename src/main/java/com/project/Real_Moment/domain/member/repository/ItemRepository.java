package com.project.Real_Moment.domain.member.repository;

import com.project.Real_Moment.domain.member.entity.Item;
import com.project.Real_Moment.domain.member.repository.custom.ItemRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {
}
