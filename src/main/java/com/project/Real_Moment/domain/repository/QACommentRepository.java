package com.project.Real_Moment.domain.repository;

import com.project.Real_Moment.domain.entity.QAComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QACommentRepository extends JpaRepository<QAComment, Long> {
}
