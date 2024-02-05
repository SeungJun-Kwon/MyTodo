package com.sparta.mytodo.repository;

import com.sparta.mytodo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
