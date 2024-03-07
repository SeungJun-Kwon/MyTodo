package com.sparta.mytodo.domain.comment.repository;

import com.sparta.mytodo.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
