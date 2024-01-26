package com.project.Real_Moment.controller.member;

import com.project.Real_Moment.dto.OrdersListDto;
import com.project.Real_Moment.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MyPageController {

    private final MyPageService myPageService;

    // 마이 페이지 (주문 목록)
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_MEMBER')")
    public ResponseEntity<List<OrdersListDto>> myPageMain(@PathVariable("id") Long id) {

        return ResponseEntity.ok(myPageService.findOrdersList(id));
    }
}
