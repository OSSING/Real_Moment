package com.project.Real_Moment.presentation.admin;

import com.amazonaws.services.s3.AmazonS3Client;
import com.project.Real_Moment.application.admin.AdminItemService;
import com.project.Real_Moment.presentation.dto.CondDto;
import com.project.Real_Moment.presentation.dto.ItemDto;
import com.project.Real_Moment.presentation.dto.S3FileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @GetMapping("/admin/item")
    public ResponseEntity<ItemDto.AdminItemDef> getItemDef(@RequestParam("itemId") Long itemId) {
        return ResponseEntity.ok().body(adminItemService.getItemDef(itemId));
    }

    @PostMapping("/admin/item")
    public ResponseEntity<Void> saveItem(@RequestPart("request") ItemDto.SaveItem dto,
                                         @RequestPart("mainImg") List<MultipartFile> mainImgList,
                                         @RequestPart("subImg") List<MultipartFile> subImgList) throws IOException {
        adminItemService.saveItem(dto, mainImgList, subImgList);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/admin/item/data")
    public ResponseEntity<ItemDto.EditItemClick> editItemClick(@RequestParam("itemId") Long itemId) {
        return ResponseEntity.ok().body(adminItemService.editItemClick(itemId));
    }

    @PatchMapping("/admin/item/data")
    public ResponseEntity<Void> editItem(@RequestBody ItemDto.EditItem dto) {
        adminItemService.editItem(dto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/admin/item/mainImg")
    public ResponseEntity<Void> editItemMainImg(@RequestPart("itemId") ItemDto.ItemIdRequestPart itemId,
                                                @RequestPart("mainImg") List<MultipartFile> mainImgList,
                                                @RequestPart("s3FileId") S3FileDto.s3FileIdRequestPart s3FileId) {
        adminItemService.editItemMainImg(itemId, mainImgList, s3FileId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/admin/item/subImg")
    public ResponseEntity<Void> editItemSubImg(@RequestPart("itemId") ItemDto.ItemIdRequestPart itemId,
                                                @RequestPart("subImg") List<MultipartFile> subImgList,
                                                @RequestPart("s3FileId") S3FileDto.s3FileIdRequestPart s3FileId) {
        adminItemService.editItemSubImg(itemId, subImgList, s3FileId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/admin/item")
    public ResponseEntity<Void> deleteItem(@RequestParam("itemId") Long itemId) {
        adminItemService.deleteItem(itemId);
        return ResponseEntity.ok().build();
    }
}
