package com.project.Real_Moment.presentation.admin;

import com.project.Real_Moment.application.admin.AdminQACommentService;
import com.project.Real_Moment.presentation.dto.QACommentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminQACommentController {

    private final AdminQACommentService adminQACommentService;

    @PostMapping("/admin/{id}/QAComment")
    public ResponseEntity<Void> saveQAComment(@PathVariable("id") Long id, @RequestBody QACommentDto.SaveQACommentResponse dto) {
        adminQACommentService.saveQAComment(id, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/admin/{id}/QAComment/data")
    public ResponseEntity<QACommentDto.EditQAComment> editQACommentClick(@RequestParam("qaCommentId") Long qaCommentId) {
        return ResponseEntity.ok().body(adminQACommentService.editQACommentClick(qaCommentId));
    }

    @PatchMapping("/admin/{id}/QAComment")
    public ResponseEntity<Void> editQAComment(@PathVariable("id") Long id, @RequestBody QACommentDto.EditQAComment dto) {
        adminQACommentService.editQAComment(id, dto);
        return ResponseEntity.ok().build();
    }
}
