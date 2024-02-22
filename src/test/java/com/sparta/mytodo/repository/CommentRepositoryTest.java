package com.sparta.mytodo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.sparta.mytodo.dto.CommentRequestDto;
import com.sparta.mytodo.dto.TodoCardRequestDto;
import com.sparta.mytodo.entity.Comment;
import com.sparta.mytodo.entity.TodoCard;
import com.sparta.mytodo.entity.User;
import com.sparta.mytodo.entity.UserRoleEnum;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CommentRepositoryTest extends RepositoryTest {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TodoCardRepository todoCardRepository;

    private static User user;
    private static TodoCard todoCard;

    @BeforeAll
    static void beforeAll() {
        user = new User("abc123", "abc123", UserRoleEnum.USER);
        todoCard = new TodoCard(
            TodoCardRequestDto.builder().cardname("카드 이름").content("카드 내용").build(), user);
    }

    @BeforeEach
    void setUp() {
        userRepository.save(user);
        todoCardRepository.save(todoCard);
    }

    @Test
    @DisplayName("Comment 생성")
    void createComment() {
        Comment comment = new Comment();
        String content = "댓글 내용";
        comment.setContent(content);
        comment.setUser(user);
        comment.setTodoCard(todoCard);

        Comment createdComment = commentRepository.save(comment);

        assertNotNull(createdComment);
        assertEquals(content, createdComment.getContent());
        assertEquals(user, createdComment.getUser());
        assertEquals(todoCard, createdComment.getTodoCard());
        assertNotNull(createdComment.getCreatedAt());
    }

    @Test
    @DisplayName("Comment 수정")
    void updateComment() {
        Comment comment = new Comment(CommentRequestDto.builder().content("댓글 내용").build(), user,
            todoCard);
        commentRepository.save(comment);

        String content = "내용 수정";
        if (commentRepository.findById(comment.getId()).isPresent()) {
            comment.setContent(content);
        }

        assertEquals(content, comment.getContent());
    }

    @Test
    @DisplayName("Comment 삭제")
    void deleteComment() {
        Comment comment = new Comment(CommentRequestDto.builder().content("댓글 내용").build(), user,
            todoCard);
        commentRepository.save(comment);

        if (commentRepository.findById(comment.getId()).isPresent()) {
            commentRepository.delete(comment);
        }
        comment = commentRepository.findById(comment.getId()).orElse(null);

        assertNull(comment);
    }
}