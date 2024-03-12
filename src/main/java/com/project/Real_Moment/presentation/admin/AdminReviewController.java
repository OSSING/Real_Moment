package com.project.Real_Moment.presentation.admin;

import com.project.Real_Moment.application.admin.AdminReviewService;
import com.project.Real_Moment.presentation.dto.CondDto;
import com.project.Real_Moment.presentation.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminReviewController {

    private final AdminReviewService adminReviewService;

    @GetMapping("/admin/reviewList")
    public ResponseEntity<ReviewDto.ItemDetReviewResponse> getReviewList(@RequestParam("itemId") Long itemId,
                                                                         @RequestParam("star") Integer star,
                                                                         @RequestParam("nowPage") int nowPage) {
        CondDto.ReviewListCond dto = new CondDto.ReviewListCond(itemId, star, nowPage);
        return ResponseEntity.ok().body(adminReviewService.getReviewList(dto));
    }
}
