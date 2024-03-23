package com.sparta.mytodo.domain.comment.repository;

import com.sparta.mytodo.domain.comment.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where c.todo.todoId = ?1")
    List<Comment> findAllByTodoId(Long todoId);
}
