package com.project.Real_Moment.presentation.member;

import com.project.Real_Moment.application.member.ReviewService;
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
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/reviewList")
    public ResponseEntity<ReviewDto.ItemDetReviewResponse> getItemDetReview(@RequestParam("itemId") Long id, @RequestParam(value = "star", required = false) Integer star, @RequestParam("nowPage") int nowPage) {
        return ResponseEntity.ok().body(reviewService.getItemDetReview(id, star, nowPage));
    }
}
