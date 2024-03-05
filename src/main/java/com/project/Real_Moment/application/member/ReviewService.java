package com.project.Real_Moment.application.member;

import com.project.Real_Moment.domain.member.entity.Item;
import com.project.Real_Moment.domain.member.repository.ItemRepository;
import com.project.Real_Moment.domain.member.repository.ReviewRepository;
import com.project.Real_Moment.presentation.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public ReviewDto.ItemDetReviewResponse getItemDetReview(Long itemId, Integer star, int nowPage) {
        Item item = itemRepository.findById(itemId).orElseThrow(IllegalArgumentException::new);
        return reviewRepository.findReviewListByItemIdOrStar(itemId, star, nowPage);
    }
}
