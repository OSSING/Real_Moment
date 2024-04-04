package com.project.Real_Moment.domain.repository.custom;

import com.project.Real_Moment.domain.entity.Item;
import com.project.Real_Moment.domain.entity.ItemFile;

import java.util.List;

public interface ItemFileRepositoryCustom {

    List<ItemFile> findByItemId(Item item);

    List<ItemFile> findByItemIdWithS3File(Long itemId);

    List<ItemFile> findImgListByGoeNumber(Item item, int number, String imgType);

    void updateGoeNumberPlus(ItemFile itemFile);

    void updateGoeNumberMinus(ItemFile itemFile);

    void updateChangeNumber(Item item, int number1, int number2, String imgType);
}
