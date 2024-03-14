package com.sparta.mytodo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.sparta.mytodo.domain.comment.dto.CommentRequestDto;
import com.sparta.mytodo.domain.comment.entity.Comment;
import com.sparta.mytodo.domain.comment.repository.CommentRepository;
import com.sparta.mytodo.domain.todo.entity.Todo;
import com.sparta.mytodo.domain.todo.repository.TodoRepository;
import com.sparta.mytodo.domain.user.entity.User;
import com.sparta.mytodo.domain.user.repository.UserRepository;
import jakarta.persistence.EntityListeners;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
class CommentRepositoryTest extends RepositoryTest {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TodoRepository todoRepository;

    @Test
    @DisplayName("Comment 생성")
    void createComment() {
        Comment comment = new Comment();
        String content = "댓글 내용";
        comment.setContent(content);
        comment.setUser(User.builder().userId(100L).build());
        comment.setTodo(Todo.builder().todoId(100L).build());

        Comment createdComment = commentRepository.save(comment);

        assertNotNull(createdComment);
        assertEquals(content, createdComment.getContent());
    }

    @Test
    @DisplayName("Comment 수정")
    void updateComment() {
        Comment comment = new Comment(CommentRequestDto.builder().content("댓글 내용").build(),
            User.builder().userId(100L).build(),
            Todo.builder().todoId(100L).build());
        commentRepository.save(comment);

        String content = "내용 수정";
        if (commentRepository.findById(comment.getCommentId()).isPresent()) {
            comment.setContent(content);
        }

        assertEquals(content, comment.getContent());
    }

    @Test
    @DisplayName("Comment 삭제")
    void deleteComment() {
        Comment comment = new Comment(CommentRequestDto.builder().content("댓글 내용").build(),
            User.builder().userId(100L).build(),
            Todo.builder().todoId(100L).build());
        commentRepository.save(comment);

        if (commentRepository.findById(comment.getCommentId()).isPresent()) {
            commentRepository.delete(comment);
        }
        comment = commentRepository.findById(comment.getCommentId()).orElse(null);

        assertNull(comment);
    }
}