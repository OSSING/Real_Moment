package com.project.Real_Moment.domain.member.repository;

import com.project.Real_Moment.domain.member.entity.ItemQA;
import com.project.Real_Moment.domain.member.repository.custom.ItemQARepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemQARepository extends JpaRepository<ItemQA, Long>, ItemQARepositoryCustom {

}
