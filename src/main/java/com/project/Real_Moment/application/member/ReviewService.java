package com.project.Real_Moment.application.member;

import com.project.Real_Moment.domain.entity.Item;
import com.project.Real_Moment.domain.entity.Review;
import com.project.Real_Moment.domain.repository.ItemRepository;
import com.project.Real_Moment.domain.repository.ReviewRepository;
import com.project.Real_Moment.presentation.dto.CondDto;
import com.project.Real_Moment.presentation.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public ReviewDto.ItemDetReviewResponse getItemDetReview(CondDto.ReviewListCond requestDto) {
        Pageable pageable = PageRequest.of(requestDto.getNowPage() - 1, 5);

        Item item = itemRepository.findById(requestDto.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        Page<Review> reviewListPaging = reviewRepository.findMyReviewListByPaging(pageable, requestDto);

        List<ReviewDto.ReviewListResponse> reviewListDto = reviewListPaging.stream()
                .map(ReviewDto.ReviewListResponse::new)
                .toList();

        return new ReviewDto.ItemDetReviewResponse(reviewListDto, reviewListPaging.getTotalPages(), requestDto.getNowPage());
    }
}
