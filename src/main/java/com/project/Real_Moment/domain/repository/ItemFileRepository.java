package com.project.Real_Moment.domain.repository;

import com.project.Real_Moment.domain.entity.ItemFile;
import com.project.Real_Moment.domain.repository.custom.ItemFileRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemFileRepository extends JpaRepository<ItemFile, Long>, ItemFileRepositoryCustom {
}
