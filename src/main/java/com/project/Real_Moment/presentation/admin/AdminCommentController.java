package com.project.Real_Moment.presentation.admin;

import com.project.Real_Moment.application.admin.AdminCommentService;
import com.project.Real_Moment.presentation.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminCommentController {

    private final AdminCommentService adminCommentService;

    @PostMapping("/admin/{id}/comment")
    public ResponseEntity<Void> saveComment(@PathVariable("id") Long id, @RequestBody CommentDto.CommentRequest dto) {
        adminCommentService.saveComment(id, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/admin/{id}/comment")
    public ResponseEntity<CommentDto.CommentResponse> editCommentClick(@PathVariable("id") Long id, @RequestParam("commentId") Long commentId) {
        return ResponseEntity.ok().body(adminCommentService.editCommentClick(id, commentId));
    }

    @PatchMapping("admin/{id}/comment")
    public ResponseEntity<Void> editComment(@PathVariable("id") Long id, @RequestBody CommentDto.CommentResponse dto) {
        adminCommentService.editComment(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/admin/{id}/comment")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") Long id, @RequestParam("commentId") Long commentId) {
        adminCommentService.deleteComment(id, commentId);
        return ResponseEntity.ok().build();
    }
}
