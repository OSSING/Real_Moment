package com.project.Real_Moment.application.admin;

import com.project.Real_Moment.domain.entity.Admin;
import com.project.Real_Moment.domain.entity.ItemQA;
import com.project.Real_Moment.domain.entity.QAComment;
import com.project.Real_Moment.domain.repository.AdminRepository;
import com.project.Real_Moment.domain.repository.ItemQARepository;
import com.project.Real_Moment.domain.repository.QACommentRepository;
import com.project.Real_Moment.presentation.dto.QACommentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminQACommentService {

    private final QACommentRepository qaCommentRepository;
    private final ItemQARepository itemQARepository;
    private final AdminRepository adminRepository;

    @Transactional
    public void saveQAComment(Long adminId, QACommentDto.SaveQACommentResponse dto) {
        /**
         * adminId의 URL과 AccessToken의 admin 정보로 검증이 완료된 후
         */
        Admin admin = adminRepository
                .findById(adminId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 관리자입니다."));

        ItemQA itemQA = checkItemQAValidity(dto.getItemQAId());

        qaCommentRepository.save(dto.toEntity(admin, itemQA));

        // 댓글을 작성할 상품 문의에 isAnswer이 false라면 true로 변경
        if (!itemQA.isAnswer()) {
            itemQARepository.updateItemQAByIsAnswer(dto.getItemQAId());
        }
    }

    @Transactional(readOnly = true)
    public QACommentDto.EditQAComment editQACommentClick(Long qaCommentId) {
        QAComment qaComment = checkQACommentValidity(qaCommentId);

        return new QACommentDto.EditQAComment(qaComment);
    }

    @Transactional
    public void editQAComment(Long adminId, QACommentDto.EditQAComment dto) {
        QAComment qaComment = checkQACommentValidity(dto.getQaCommentId());

        if (!qaComment.getAdminId().getId().equals(adminId)) {
            throw new IllegalArgumentException("댓글을 작성한 관리자가 아닙니다.");
        }

        qaCommentRepository.updateById(dto);
    }

    private ItemQA checkItemQAValidity(Long itemQAId) {
        return itemQARepository.findById(itemQAId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ItemQA입니다."));
    }

    private QAComment checkQACommentValidity(Long qaCommentId) {
        return qaCommentRepository.findById(qaCommentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 QAComment입니다."));
    }
}
