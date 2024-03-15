package com.project.Real_Moment.domain.repository;

import com.project.Real_Moment.domain.entity.Comment;
import com.project.Real_Moment.domain.entity.OneOnOne;
import com.project.Real_Moment.domain.repository.custom.CommentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

     boolean existsByOneOnOneId(OneOnOne oneOnOne);
}
