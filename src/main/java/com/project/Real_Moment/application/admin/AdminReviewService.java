package com.project.Real_Moment.application.admin;

import com.project.Real_Moment.domain.entity.Review;
import com.project.Real_Moment.domain.repository.AdminRepository;
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
public class AdminReviewService {

    private final ReviewRepository reviewRepository;
    private final AdminRepository adminRepository;


    @Transactional(readOnly = true)
    public ReviewDto.ItemDetReviewResponse getReviewList(CondDto.ReviewListCond dto) {
        Pageable pageable = PageRequest.of(dto.getNowPage() - 1, 10);

        Page<Review> reviewListPaging = reviewRepository.findReviewListByCond(pageable, dto);

        List<ReviewDto.ReviewListResponse> reviewDto = reviewListPaging.stream()
                .map(ReviewDto.ReviewListResponse::new)
                .toList();

        return new ReviewDto.ItemDetReviewResponse(reviewDto, reviewListPaging.getTotalPages(), dto.getNowPage());
    }
}
