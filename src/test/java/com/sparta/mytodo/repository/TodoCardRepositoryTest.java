package com.sparta.mytodo.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.sparta.mytodo.dto.TodoCardRequestDto;
import com.sparta.mytodo.entity.TodoCard;
import com.sparta.mytodo.entity.User;
import com.sparta.mytodo.entity.UserRoleEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TodoCardRepositoryTest extends RepositoryTest {

    @Autowired
    private TodoCardRepository todoCardRepository;
    @Autowired
    private UserRepository userRepository;

    private static User user;

    @BeforeAll
    static void beforeAll() {
        user = new User("abc123", "abc123", UserRoleEnum.USER);
    }

    @BeforeEach
    void setUp() {
        userRepository.save(user);
    }

    @Test
    @DisplayName("Todo 생성")
    void createTodo() {
        TodoCard todoCard = new TodoCard();
        String cardname = "카드 이름";
        String content = "카드 내용";
        boolean isfinished = false;
        todoCard.setCardname(cardname);
        todoCard.setContent(content);
        todoCard.setIsfinished(isfinished);
        todoCard.setUser(user);

        TodoCard createdTodoCard = todoCardRepository.save(todoCard);

        assertNotNull(createdTodoCard);
        assertEquals(cardname, createdTodoCard.getCardname());
        assertEquals(content, createdTodoCard.getContent());
        assertEquals(isfinished, createdTodoCard.isIsfinished());
    }

    @Test
    @DisplayName("Todo 수정")
    void updateTodo() {
        TodoCard todoCard = new TodoCard(
            TodoCardRequestDto.builder().cardname("카드 이름").content("카드 내용").build(),
            user);
        todoCardRepository.save(todoCard);

        String cardname = "카드 이름 수정";
        String content = "내용 수정";
        boolean finished = true;
        if(todoCardRepository.findById(todoCard.getId()).isPresent()) {
            todoCard.setCardname(cardname);
            todoCard.setContent(content);
            todoCard.setIsfinished(finished);
        }

        assertEquals(cardname, todoCard.getCardname());
        assertEquals(content, todoCard.getContent());
        assertEquals(finished, todoCard.isIsfinished());
    }

    @Test
    @DisplayName("Todo 삭제")
    void deleteTodo() {
        TodoCard todoCard = new TodoCard(
            TodoCardRequestDto.builder().cardname("카드 이름").content("카드 내용").build(),
            user);
        todoCardRepository.save(todoCard);

        if(todoCardRepository.findById(todoCard.getId()).isPresent()) {
            todoCardRepository.delete(todoCard);
        }
        todoCard = todoCardRepository.findById(todoCard.getId()).orElse(null);

        assertNull(todoCard);
    }
}
