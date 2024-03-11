package com.project.Real_Moment.domain.repository;

import com.project.Real_Moment.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
