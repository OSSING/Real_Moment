package com.project.Real_Moment.presentation.member;

import com.project.Real_Moment.application.member.ItemService;
import com.project.Real_Moment.presentation.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/itemList")
    public ResponseEntity<ItemDto.ItemCondResponse> getItemList(@RequestBody ItemDto.ItemCondRequest dto) {
        return ResponseEntity.ok().body(itemService.getItemList(dto));
    }

    @GetMapping("/item")
    public ResponseEntity<ItemDto.ItemDetResponse> getItemDet(@RequestParam("itemId") Long id) {
        return ResponseEntity.ok().body(itemService.getItemDet(id));
    }
}
