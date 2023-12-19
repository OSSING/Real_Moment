package com.project.shoppingmall.repositroy;

import com.project.shoppingmall.domain.QAComments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QACommentsRepository extends JpaRepository<QAComments, Long> {
}
