package com.project.Real_Moment.application.admin;

import com.project.Real_Moment.domain.entity.Admin;
import com.project.Real_Moment.domain.entity.Comment;
import com.project.Real_Moment.domain.entity.OneOnOne;
import com.project.Real_Moment.domain.repository.AdminRepository;
import com.project.Real_Moment.domain.repository.CommentRepository;
import com.project.Real_Moment.domain.repository.OneOnOneRepository;
import com.project.Real_Moment.presentation.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminCommentService {

    private final CommentRepository commentRepository;
    private final AdminRepository adminRepository;
    private final OneOnOneRepository oneOnOneRepository;

    @Transactional
    public void saveComment(Long adminId, CommentDto.CommentRequest dto) {

        // 1대1 문의 검증
        OneOnOne oneOnOne = oneOnOneRepository.findById(dto.getOneOnOneId())
                .orElseThrow(() -> new IllegalArgumentException("존재하는 OneOnOne이 아닙니다."));

        Admin admin = adminCheckValidity(adminId);

        commentRepository.save(dto.toEntity(admin, oneOnOne));

        // 문의에 댓글이 달리면, OneOnOne의 isAnswer = true 처리
        if (!oneOnOne.isAnswer()) {
            oneOnOneRepository.updateOneOnOneByComment(dto.getOneOnOneId());
        }
    }

    @Transactional(readOnly = true)
    // 관리자 검증 (AccessToken과 URL 비교로 대체 가능)
    public CommentDto.CommentResponse editCommentClick(Long adminId, Long commentId) {

        adminCheckValidity(adminId);
        Comment comment = commentCheckValidity(commentId);

        return new CommentDto.CommentResponse(comment);
    }

    @Transactional
    public void editComment(Long adminId, CommentDto.CommentResponse dto) {
        adminCheckValidity(adminId);
        Comment comment = commentCheckValidity(dto.getCommentId());

        commentRepository.updateComment(dto);
    }

    @Transactional
    public void deleteComment(Long adminId, Long commentId) {
        adminCheckValidity(adminId);
        Comment comment = commentCheckValidity(commentId);
        OneOnOne oneOnOne = comment.getOneOnOneId();

        commentRepository.delete(comment);

        // 댓글을 삭제했을 때 남은 댓글이 없다면, OneOnOne의 isAnswer = false 처리
        if (!commentRepository.existsByOneOnOneId(oneOnOne)) {
            oneOnOneRepository.updateOneOnOneById(oneOnOne.getId());
        }
    }

    private Admin adminCheckValidity(Long adminId) {
        return adminRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("존재하는 관리자가 아닙니다."));
    }

    // 댓글 검증

    private Comment commentCheckValidity(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하는 Comment가 아닙니다."));
    }
}
