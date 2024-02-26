package com.project.Real_Moment.domain.member.repository.custom;

import com.project.Real_Moment.domain.member.entity.Member;

import java.time.LocalDate;

public interface MemberRepositoryCustom {

    Member updatePasswordById(Long id, String password);

    Member updateEmailById(Long id, String email);

    Member updateNameById(Long id, String name);

    Member updateBirthDateById(Long id, LocalDate birthDate);

//    Member updateTelById(Long id, String tel);

    Long updateActivatedById(Long id);

}
