package com.sparta.mytodo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

import com.sparta.mytodo.domain.comment.dto.CommentRequestDto;
import com.sparta.mytodo.domain.comment.dto.CommentResponseDto;
import com.sparta.mytodo.domain.comment.service.CommentService;
import com.sparta.mytodo.domain.todo.dto.TodoRequestDto;
import com.sparta.mytodo.domain.comment.entity.Comment;
import com.sparta.mytodo.domain.todo.entity.Todo;
import com.sparta.mytodo.domain.user.entity.User;
import com.sparta.mytodo.global.security.UserRoleEnum;
import com.sparta.mytodo.domain.comment.repository.CommentRepository;
import com.sparta.mytodo.domain.todo.repository.TodoRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;
    @Mock
    TodoRepository todoRepository;

    CommentService commentService;

    @BeforeEach
    void setUp() {
        commentService = new CommentService(commentRepository, todoRepository);
    }

    @Test
    @DisplayName("Create Comment")
    void createComment() {
        // given
        User user = new User("abc123@naver.com", "abc123", "abc12345", UserRoleEnum.USER);

        Long todoId = 100L;
        Todo todo = new Todo(TodoRequestDto.builder().todoName("Todo 이름")
            .content("Todo 내용").build(), user);
        todo.setTodoId(todoId);
        given(todoRepository.findById(todoId)).willReturn(Optional.of(todo));

        String content = "댓글 내용";

        // when
        CommentResponseDto responseDto = commentService.createComment(todoId,
            CommentRequestDto.builder().content(content).build(),
            user);

        // then
        assertNotNull(responseDto);
        assertEquals(content, responseDto.getContent());
        assertEquals(user.getUserName(), responseDto.getUserName());
        assertEquals(todo.getTodoName(), responseDto.getTodoName());
    }

    @Test
    @DisplayName("Update Comment")
    void updateComment() {
        // given
        Long userId = 100L;
        User user = new User("abc123@naver.com", "abc123", "abc12345", UserRoleEnum.USER);
        user.setUserId(userId);

        Todo todo = new Todo(TodoRequestDto.builder().todoName("Todo 이름")
            .content("Todo 내용").build(), user);

        Long commentId = 100L;
        Comment comment = new Comment(CommentRequestDto.builder().content("댓글 내용").build(), user,
            todo);
        comment.setCommentId(commentId);
        given(commentRepository.findById(commentId)).willReturn(Optional.of(comment));

        String content = "댓글 내용 수정";

        // when
        CommentResponseDto responseDto = commentService.updateComment(commentId,
            CommentRequestDto.builder().content(content).build(), user);

        // then
        assertNotNull(responseDto);
        assertEquals(content, responseDto.getContent());
        assertEquals(user.getUserName(), responseDto.getUserName());
        assertEquals(todo.getTodoName(), responseDto.getTodoName());
    }

    @Test
    @DisplayName("Delete Comment")
    void deleteComment() {
        // given
        Long userId = 100L;
        User user = new User("abc123@naver.com", "abc123", "abc12345", UserRoleEnum.USER);
        user.setUserId(userId);

        Todo todo = new Todo(TodoRequestDto.builder().todoName("Todo 이름")
            .content("Todo 내용").build(), user);

        Long commentId = 100L;
        Comment comment = new Comment(CommentRequestDto.builder().content("댓글 내용").build(), user,
            todo);
        comment.setCommentId(commentId);
        given(commentRepository.findById(commentId)).willReturn(Optional.of(comment));

        // when
        Long deletedCommentId = commentService.deleteComment(commentId, user);

        // then
        assertEquals(commentId, deletedCommentId);
    }
}