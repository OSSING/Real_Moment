package com.project.Real_Moment.application.admin;

import com.project.Real_Moment.domain.entity.Grade;
import com.project.Real_Moment.domain.entity.Member;
import com.project.Real_Moment.domain.repository.GradeRepository;
import com.project.Real_Moment.domain.repository.MemberRepository;
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
    private final MemberRepository memberRepository;
    
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

    @Transactional
    public void editGrade(GradeDto.EditGrade dto) {
        gradeCheckValidity(dto.getGradeId());
        gradeRepository.updateGrade(dto);
    }

    @Transactional
    public void deleteGrade(Long gradeId) {
        gradeRepository.delete(deleteGradeCheckValidity(gradeId));
    }

    private Grade gradeCheckValidity(Long gradeId) {
        return gradeRepository.findById(gradeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 등급입니다."));
    }

    private Grade deleteGradeCheckValidity(Long gradeId) {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 등급입니다."));

        List<Member> memberCheck = memberRepository.findMemberListByGradeId(grade);

        if (!memberCheck.isEmpty()) {
            throw new IllegalArgumentException("삭제할 등급에 속한 회원이 있습니다.");
        } else {
            return grade;
        }
    }
}
