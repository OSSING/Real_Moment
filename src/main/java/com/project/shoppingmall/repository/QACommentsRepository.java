package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.QAComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QACommentsRepository extends JpaRepository<QAComment, Long> {
}
