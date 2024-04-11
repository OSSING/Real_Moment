package com.project.Real_Moment.presentation.admin;

import com.project.Real_Moment.application.admin.AdminOneOnOneService;
import com.project.Real_Moment.presentation.dto.CondDto;
import com.project.Real_Moment.presentation.dto.OneOnOneDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminOneOnOneController {

    private final AdminOneOnOneService adminOneOnOneService;

    @GetMapping("/admin/oneOnOneList/view")
    public ResponseEntity<OneOnOneDto.OneOnOneWrapper> getOneOnOneList(@RequestParam(value = "loginId", required = false) String loginId,
                                                                       @RequestParam(value = "isAnswer", required = false) Boolean isAnswer,
                                                                       @RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage) {
        CondDto.AdminOneOnOneListCond dto = new CondDto.AdminOneOnOneListCond(loginId, isAnswer, nowPage);
        return ResponseEntity.ok().body(adminOneOnOneService.getOneOnOneList(dto));
    }
}
