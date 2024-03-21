package com.project.Real_Moment.presentation.admin;

import com.amazonaws.services.s3.AmazonS3Client;
import com.project.Real_Moment.application.admin.AdminItemService;
import com.project.Real_Moment.presentation.dto.CondDto;
import com.project.Real_Moment.presentation.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminItemController {

    private final AmazonS3Client amazonS3Client;
    private final AdminItemService adminItemService;

    @GetMapping("/admin/itemList")
    public ResponseEntity<ItemDto.AdminItemListWrapper> getItemList(@RequestParam(value = "itemSort", required = false) String itemSort,
                                                                    @RequestParam(value = "categoryId", required = false) Long categoryId,
                                                                    @RequestParam(value = "itemName", required = false) String itemName,
                                                                    @RequestParam(value = "isDelete", required = false) Boolean isDelete,
                                                                    @RequestParam(value = "nowPage", required = false) Integer nowPage) {
        CondDto.ItemListCond dto = new CondDto.ItemListCond(itemSort, categoryId, itemName, isDelete, nowPage);
        return ResponseEntity.ok().body(adminItemService.getItemList(dto));
    }
}
