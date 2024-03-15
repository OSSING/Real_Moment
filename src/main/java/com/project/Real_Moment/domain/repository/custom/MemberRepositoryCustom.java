package com.project.Real_Moment.domain.repository.custom;

import com.project.Real_Moment.domain.entity.Member;
import com.project.Real_Moment.presentation.dto.CondDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface MemberRepositoryCustom {

    Member updatePasswordById(Long id, String password);

    Member updateEmailById(Long id, String email);

    Member updateNameById(Long id, String name);

    Member updateBirthDateById(Long id, LocalDate birthDate);

    Long updateActivatedById(Long id);

    Page<Member> findMemberListByCond(Pageable pageable, CondDto.MemberListCond dto);

}
