package com.project.Real_Moment.application.admin;

import com.project.Real_Moment.domain.entity.Grade;
import com.project.Real_Moment.domain.entity.Member;
import com.project.Real_Moment.domain.repository.GradeRepository;
import com.project.Real_Moment.domain.repository.MemberRepository;
import com.project.Real_Moment.presentation.dto.CondDto;
import com.project.Real_Moment.presentation.dto.GradeDto;
import com.project.Real_Moment.presentation.dto.MemberDto;
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
public class AdminMemberService {

    private final MemberRepository memberRepository;
    private final GradeRepository gradeRepository;


    @Transactional(readOnly = true)
    public MemberDto.MemberListWrapper getMemberList(CondDto.MemberListCond dto) {
        Pageable pageable = PageRequest.of(dto.getNowPage() - 1, 10);

        Page<Member> memberListPaging = memberRepository.findMemberListByCond(pageable, dto);

        List<MemberDto.MemberList> memberListDto = memberListPaging.stream()
                .map(MemberDto.MemberList::new)
                .toList();

        for (MemberDto.MemberList memberList : memberListDto) {
            if (memberList.getGrade() == null) {
                memberList.setGrade(null);
            } else {
                Grade grade = gradeRepository.findById(memberList.getGrade().getGradeId()).orElse(null);

                GradeDto.GradeResponse gradeDto = null;

                if (grade != null) {
                    gradeDto = new GradeDto.GradeResponse(grade);
                }

                memberList.setGrade(gradeDto);
            }
        }

        return new MemberDto.MemberListWrapper(memberListDto, memberListPaging.getTotalPages(), dto.getNowPage());
    }
}
