package com.project.Real_Moment.domain.member.repository.custom;

import com.project.Real_Moment.domain.member.entity.Member;

public interface MemberRepositoryCustom {

    Member updatePasswordById(Long id, String password);

    Member updateEmailById(Long id, String email);
}
