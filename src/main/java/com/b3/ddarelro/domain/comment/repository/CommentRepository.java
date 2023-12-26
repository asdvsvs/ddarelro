package com.b3.ddarelro.domain.comment.repository;

import com.b3.ddarelro.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}