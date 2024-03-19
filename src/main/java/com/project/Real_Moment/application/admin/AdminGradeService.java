package com.project.Real_Moment.application.admin;

import com.project.Real_Moment.domain.repository.GradeRepository;
import com.project.Real_Moment.presentation.dto.GradeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminGradeService {
    
    private final GradeRepository gradeRepository;
    
    @Transactional(readOnly = true)
    public List<GradeDto.GradeResponse> getGradeList() {
        return gradeRepository.findAll()
                .stream()
                .map(GradeDto.GradeResponse::new)
                .toList();
    }

    @Transactional
    public void saveGrade(GradeDto.SaveGrade dto) {
        gradeRepository.save(dto.toEntity());
    }
}
