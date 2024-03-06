package com.sparta.mytodo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.sparta.mytodo.dto.CommentRequestDto;
import com.sparta.mytodo.dto.TodoRequestDto;
import com.sparta.mytodo.entity.Comment;
import com.sparta.mytodo.entity.Todo;
import com.sparta.mytodo.entity.User;
import com.sparta.mytodo.entity.UserRoleEnum;
import jakarta.persistence.EntityListeners;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

    private static User user;
    private static Todo todo;

    @BeforeAll
    static void beforeAll() {
        user = new User("abc123@naver.com", "abc123", "abc123", UserRoleEnum.USER);
        todo = new Todo(
            TodoRequestDto.builder().todoName("Todo 이름").content("Todo 내용").build(), user);
    }

    @BeforeEach
    void setUp() {
//        userRepository.save(user);
//        todoRepository.save(todo);
    }

    @Test
    @DisplayName("Comment 생성")
    void createComment() {
        Comment comment = new Comment();
        String content = "댓글 내용";
        comment.setContent(content);
        comment.setUser(User.builder().userId(100L).build());
        comment.setTodo(Todo.builder().todoId(100L).build());
//        comment.setUser(user);
//        comment.setTodo(todo);

        Comment createdComment = commentRepository.save(comment);

        assertNotNull(createdComment);
        assertEquals(content, createdComment.getContent());
//        assertEquals(user, createdComment.getUser());
//        assertEquals(todo, createdComment.getTodo());
    }

    @Test
    @DisplayName("Comment 수정")
    void updateComment() {
        Comment comment = new Comment(CommentRequestDto.builder().content("댓글 내용").build(), user,
            todo);
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
        Comment comment = new Comment(CommentRequestDto.builder().content("댓글 내용").build(), user,
            todo);
        commentRepository.save(comment);

        if (commentRepository.findById(comment.getCommentId()).isPresent()) {
            commentRepository.delete(comment);
        }
        comment = commentRepository.findById(comment.getCommentId()).orElse(null);

        assertNull(comment);
    }
}