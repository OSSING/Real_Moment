package com.project.Real_Moment.domain.member.repository;

import com.project.Real_Moment.domain.member.entity.QAComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QACommentsRepository extends JpaRepository<QAComment, Long> {
}
