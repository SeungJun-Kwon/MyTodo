package com.sparta.mytodo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.sparta.mytodo.dto.CommentRequestDto;
import com.sparta.mytodo.dto.CommentResponseDto;
import com.sparta.mytodo.dto.TodoCardRequestDto;
import com.sparta.mytodo.entity.Comment;
import com.sparta.mytodo.entity.TodoCard;
import com.sparta.mytodo.entity.User;
import com.sparta.mytodo.entity.UserRoleEnum;
import com.sparta.mytodo.repository.CommentRepository;
import com.sparta.mytodo.repository.TodoCardRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;
    @Mock
    TodoCardRepository todoCardRepository;

    CommentService commentService;

    @BeforeEach
    void setUp() {
        commentService = new CommentService(commentRepository, todoCardRepository);
    }

    @Test
    @DisplayName("Create Comment")
    void createComment() {
        // given
        User user = new User("abc123", "abc12345", UserRoleEnum.USER);

        Long cardId = 100L;
        TodoCard todoCard = new TodoCard(TodoCardRequestDto.builder().cardname("카드 이름")
            .content("카드 내용").build(), user);
        todoCard.setId(cardId);
        given(todoCardRepository.findById(cardId)).willReturn(Optional.of(todoCard));

        String content = "댓글 내용";

        // when
        CommentResponseDto responseDto = commentService.createComment(cardId,
            CommentRequestDto.builder().content(content).build(),
            user);

        // then
        assertNotNull(responseDto);
        assertEquals(content, responseDto.getContent());
        assertEquals(user.getUsername(), responseDto.getUsername());
        assertEquals(todoCard.getCardname(), responseDto.getCardname());
    }

    @Test
    @DisplayName("Update Comment")
    void updateComment() {
        // given
        Long userId = 100L;
        User user = new User("abc123", "abc12345", UserRoleEnum.USER);
        user.setId(userId);

        TodoCard todoCard = new TodoCard(TodoCardRequestDto.builder().cardname("카드 이름")
            .content("카드 내용").build(), user);

        Long commentId = 100L;
        Comment comment = new Comment(CommentRequestDto.builder().content("댓글 내용").build(), user,
            todoCard);
        comment.setId(commentId);
        given(commentRepository.findById(commentId)).willReturn(Optional.of(comment));

        String content = "댓글 내용 수정";

        // when
        CommentResponseDto responseDto = commentService.updateComment(commentId,
            CommentRequestDto.builder().content(content).build(), user);

        // then
        assertNotNull(responseDto);
        assertEquals(content, responseDto.getContent());
        assertEquals(user.getUsername(), responseDto.getUsername());
        assertEquals(todoCard.getCardname(), responseDto.getCardname());
    }

    @Test
    @DisplayName("Delete Comment")
    void deleteComment() {
        // given
        Long userId = 100L;
        User user = new User("abc123", "abc12345", UserRoleEnum.USER);
        user.setId(userId);

        TodoCard todoCard = new TodoCard(TodoCardRequestDto.builder().cardname("카드 이름")
            .content("카드 내용").build(), user);

        Long commentId = 100L;
        Comment comment = new Comment(CommentRequestDto.builder().content("댓글 내용").build(), user,
            todoCard);
        comment.setId(commentId);
        given(commentRepository.findById(commentId)).willReturn(Optional.of(comment));

        // when
        Long deletedCommentId = commentService.deleteComment(commentId, user);

        // then
        assertEquals(commentId, deletedCommentId);
    }
}