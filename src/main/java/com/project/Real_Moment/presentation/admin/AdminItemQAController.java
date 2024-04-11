package com.project.Real_Moment.presentation.admin;

import com.project.Real_Moment.application.admin.AdminItemQAService;
import com.project.Real_Moment.presentation.dto.CondDto;
import com.project.Real_Moment.presentation.dto.ItemQADto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminItemQAController {

    private final AdminItemQAService adminItemQAService;

    @GetMapping("/admin/QAList/view")
    public ResponseEntity<ItemQADto.ItemQAListPage> getItemQAList(@RequestParam("itemId") Long itemId,
                                                                  @RequestParam("isAnswer") boolean isAnswer,
                                                                  @RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage) {
        CondDto.QAListCond dto = new CondDto.QAListCond(itemId, isAnswer, nowPage);
        return ResponseEntity.ok().body(adminItemQAService.getItemQAList(dto));
    }
}
