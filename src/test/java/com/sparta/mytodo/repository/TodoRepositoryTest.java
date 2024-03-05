package com.sparta.mytodo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.sparta.mytodo.dto.TodoRequestDto;
import com.sparta.mytodo.entity.Todo;
import com.sparta.mytodo.entity.User;
import com.sparta.mytodo.entity.UserRoleEnum;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TodoRepositoryTest extends RepositoryTest {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private UserRepository userRepository;

    private static User user;

    @BeforeAll
    static void beforeAll() {
        user = new User("abc123@naver.com", "abc123", "abc123", UserRoleEnum.USER);
    }

    @BeforeEach
    void setUp() {
        userRepository.save(user);
    }

    @Test
    @DisplayName("Todo 생성")
    void createTodo() {
        Todo todo = new Todo();
        String todoName = "Todo 이름";
        String content = "Todo 내용";
        boolean finished = false;
        todo.setTodoName(todoName);
        todo.setContent(content);
        todo.setFinished(finished);
        todo.setUser(user);

        Todo createdTodo = todoRepository.save(todo);

        assertNotNull(createdTodo);
        assertEquals(todoName, createdTodo.getTodoName());
        assertEquals(content, createdTodo.getContent());
        assertEquals(finished, createdTodo.isFinished());
    }

    @Test
    @DisplayName("Todo 수정")
    void updateTodo() {
        Todo todo = new Todo(
            TodoRequestDto.builder().todoName("Todo 이름").content("Todo 내용").build(),
            user);
        todoRepository.save(todo);

        String todoName = "Todo 이름 수정";
        String content = "내용 수정";
        boolean finished = true;
        if (todoRepository.findById(todo.getTodoId()).isPresent()) {
            todo.setTodoName(todoName);
            todo.setContent(content);
            todo.setFinished(finished);
        }

        assertEquals(todoName, todo.getTodoName());
        assertEquals(content, todo.getContent());
        assertEquals(finished, todo.isFinished());
    }

    @Test
    @DisplayName("Todo 삭제")
    void deleteTodo() {
        Todo todo = new Todo(
            TodoRequestDto.builder().todoName("Todo 이름").content("Todo 내용").build(),
            user);
        todoRepository.save(todo);

        if (todoRepository.findById(todo.getTodoId()).isPresent()) {
            todoRepository.delete(todo);
        }
        todo = todoRepository.findById(todo.getTodoId()).orElse(null);

        assertNull(todo);
    }

    @Test
    void findAllByUserAndIsFinishedIsFalse() {
        Todo todo = new Todo(
            TodoRequestDto.builder().todoName("Todo 이름").content("Todo 내용").build(),
            user);
        todoRepository.save(todo);

        List<Todo> todoList = todoRepository.findAllByUserAndIsFinishedIsFalse(
            user.getUserId());

        assertNotNull(todoList);
    }
}
