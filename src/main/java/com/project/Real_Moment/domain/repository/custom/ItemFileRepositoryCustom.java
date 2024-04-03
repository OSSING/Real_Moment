package com.project.Real_Moment.domain.repository.custom;

import com.project.Real_Moment.domain.entity.Item;
import com.project.Real_Moment.domain.entity.ItemFile;

import java.util.List;

public interface ItemFileRepositoryCustom {

    List<ItemFile> findByItemId(Item item);

    List<ItemFile> findByItemIdWithS3File(Long itemId);

    List<ItemFile> findListByGoeNumber(Item item, int number);

    void updateGoeNumberPlus(ItemFile itemFile);

    void updateChangeNumber(Item item, int number1, int number2);

}
