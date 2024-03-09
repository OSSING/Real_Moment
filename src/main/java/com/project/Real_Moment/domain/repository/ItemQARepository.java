package com.project.Real_Moment.domain.repository;

import com.project.Real_Moment.domain.entity.ItemQA;
import com.project.Real_Moment.domain.repository.custom.ItemQARepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemQARepository extends JpaRepository<ItemQA, Long>, ItemQARepositoryCustom {

}
