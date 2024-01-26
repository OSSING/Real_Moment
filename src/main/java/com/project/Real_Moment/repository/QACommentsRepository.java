package com.project.Real_Moment.repository;

import com.project.Real_Moment.entity.QAComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QACommentsRepository extends JpaRepository<QAComment, Long> {
}
