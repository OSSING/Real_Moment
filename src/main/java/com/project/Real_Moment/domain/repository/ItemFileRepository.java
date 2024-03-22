package com.project.Real_Moment.domain.repository;

import com.project.Real_Moment.domain.entity.Item;
import com.project.Real_Moment.domain.entity.ItemFile;
import com.project.Real_Moment.domain.repository.custom.ItemFileRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemFileRepository extends JpaRepository<ItemFile, Long>, ItemFileRepositoryCustom {
    List<ItemFile> findByItemId(Item item);
}
