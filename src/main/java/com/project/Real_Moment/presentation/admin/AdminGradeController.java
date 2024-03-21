package com.project.Real_Moment.presentation.admin;

import com.project.Real_Moment.application.admin.AdminGradeService;
import com.project.Real_Moment.presentation.dto.GradeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminGradeController {

    private final AdminGradeService adminGradeService;

    @GetMapping("/admin/gradeList")
    public ResponseEntity<List<GradeDto.GradeResponse>> getGradeList() {
        return ResponseEntity.ok().body(adminGradeService.getGradeList());
    }

    @PostMapping("/admin/grade")
    public ResponseEntity<Void> saveGrade(@RequestBody GradeDto.SaveGrade dto) {
        adminGradeService.saveGrade(dto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/admin/grade")
    public ResponseEntity<Void> editGrade(@RequestBody GradeDto.EditGrade dto) {
        adminGradeService.editGrade(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/admin/grade")
    public ResponseEntity<Void> deleteGrade(@RequestParam("gradeId") Long gradeId) {
        adminGradeService.deleteGrade(gradeId);
        return ResponseEntity.ok().build();
    }
}

