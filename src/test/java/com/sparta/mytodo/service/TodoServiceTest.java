package com.sparta.mytodo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

import com.sparta.mytodo.domain.todo.dto.TodoRequestDto;
import com.sparta.mytodo.domain.todo.dto.TodoResponseDto;
import com.sparta.mytodo.domain.todo.entity.Todo;
import com.sparta.mytodo.domain.todo.repository.TodoRepository;
import com.sparta.mytodo.domain.todo.service.TodoService;
import com.sparta.mytodo.domain.user.entity.User;
import com.sparta.mytodo.global.security.UserRoleEnum;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    TodoRepository todoRepository;

    TodoService todoService;

    @BeforeEach
    void setUp() {
        todoService = new TodoService(todoRepository);
    }

    @Test
    @DisplayName("Create Todo")
    void createTodo() {
        // given
        User user = new User("abc123@naver.com", "abc123", "abc12345", UserRoleEnum.USER);
        String todoName = "Todo 이름";
        String content = "Todo 내용";
        TodoRequestDto requestDto = TodoRequestDto.builder().todoName(todoName)
            .content(content).build();

        // when
        TodoResponseDto responseDto = todoService.createTodo(requestDto, user);

        // then
        assertNotNull(responseDto);
        assertEquals(todoName, responseDto.getTodoName());
        assertEquals(content, responseDto.getContent());
    }

    @Test
    @DisplayName("Get Todos By User")
    void getTodosByUser() {
        // given
        Long userId = 100L;
        User user = new User("abc123@naver.com", "abc123", "abc12345", UserRoleEnum.USER);
        user.setUserId(userId);

        List<Todo> todoList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            todoList.add(new Todo(TodoRequestDto.builder().todoName("Todo 이름" + (i + 1))
                .content("Todo 내용" + (i + 1)).build(), user));
        }

        int page = 0;
        int size = 5;
        boolean isAsc = true;
        String sortBy = "createdAt";

        // 페이징 처리
        Sort.Direction direction = Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Todo> todoPage = new PageImpl<>(todoList, pageable, todoList.size());

        given(todoRepository.findAllByUser(user, pageable)).willReturn(todoPage);

        // when
        Page<TodoResponseDto> result = todoService.getTodosByUser(userId, page, size, isAsc, sortBy,
            user);

        // then
        assertEquals(todoList.size(), result.getTotalElements());
        assertEquals("Todo 이름1", result.getContent().get(0).getTodoName());
        assertEquals("Todo 이름2", result.getContent().get(1).getTodoName());
    }

    @Test
    @DisplayName("Get Todos")
    void getTodos() {
        // given
        Long userId = 100L;
        User user = new User("abc123@naver.com", "abc123", "abc12345", UserRoleEnum.USER);
        user.setUserId(userId);

        Todo todo1 = new Todo(TodoRequestDto.builder().todoName("Todo 이름1")
            .content("Todo 내용1").build(), user);
        Todo todo2 = new Todo(TodoRequestDto.builder().todoName("Todo 이름2")
            .content("Todo 내용2").build(), user);

        List<Todo> todoList = new ArrayList<>();
        todoList.add(todo1);
        todoList.add(todo2);

        given(todoRepository.findAllByOrderByUser()).willReturn(Optional.of(todoList));

        // when
        List<TodoResponseDto> findTodoTodoDtoList = todoService.getTodos();

        List<Todo> findTodoList = new ArrayList<>();

        for (TodoResponseDto dto : findTodoTodoDtoList) {
            findTodoList.add(new Todo(
                TodoRequestDto.builder().todoName(dto.getTodoName()).content(dto.getContent())
                    .build(), user));
        }

        System.out.println("Find Todos = " + findTodoList.get(0).getTodoName());
        System.out.println("Find Todos = " + findTodoList.get(1).getTodoName());

        // then
        assertEquals(findTodoList.get(0).getTodoName(), todo1.getTodoName());
        assertEquals(findTodoList.get(1).getTodoName(), todo2.getTodoName());
    }

    @Test
    @DisplayName("Get Todos Paging")
    void getTodosPaging() {
        // given
        Long userId = 100L;
        User user = new User("abc123@naver.com", "abc123", "abc12345", UserRoleEnum.USER);
        user.setUserId(userId);

        Todo todo1 = new Todo(TodoRequestDto.builder().todoName("Todo 이름1")
            .content("Todo 내용1").build(), user);
        Todo todo2 = new Todo(TodoRequestDto.builder().todoName("Todo 이름2")
            .content("Todo 내용2").build(), user);

        List<Todo> todoList = new ArrayList<>();
        todoList.add(todo1);
        todoList.add(todo2);

        given(todoRepository.findAllByOrderByUser()).willReturn(Optional.of(todoList));

        // when
        List<TodoResponseDto> findTodoTodoDtoList = todoService.getTodos();

        List<Todo> findTodoList = new ArrayList<>();

        for (TodoResponseDto dto : findTodoTodoDtoList) {
            findTodoList.add(new Todo(
                TodoRequestDto.builder().todoName(dto.getTodoName()).content(dto.getContent())
                    .build(), user));
        }

        System.out.println("Find Todos = " + findTodoList.get(0).getTodoName());
        System.out.println("Find Todos = " + findTodoList.get(1).getTodoName());

        // then
        assertEquals(findTodoList.get(0).getTodoName(), todo1.getTodoName());
        assertEquals(findTodoList.get(1).getTodoName(), todo2.getTodoName());
    }

    @Test
    @DisplayName("Update Todo")
    void updateTodo() {
        // given
        Long userId = 100L;
        User user = new User("abc123@naver.com", "abc123", "abc12345", UserRoleEnum.USER);
        user.setUserId(userId);

        Long todoId = 100L;
        Todo todo = new Todo(TodoRequestDto.builder().todoName("Todo 이름")
            .content("Todo 내용").build(), user);
        todo.setTodoId(todoId);

        given(todoRepository.findById(todoId)).willReturn(Optional.of(todo));

        String todoName = "Todo 이름 수정";
        String content = "Todo 내용 수정";

        // when
        TodoResponseDto responseDto = todoService.updateTodo(todoId,
            TodoRequestDto.builder().todoName(todoName).content(content).build(), user);

        // then
        assertEquals(todoName, responseDto.getTodoName());
        assertEquals(content, responseDto.getContent());
    }

    @Test
    @DisplayName("Finish Todo")
    void finishTodo() {
        // given
        Long userId = 100L;
        User user = new User("abc123@naver.com", "abc123", "abc12345", UserRoleEnum.USER);
        user.setUserId(userId);

        Long todoId = 100L;
        Todo todo = new Todo(TodoRequestDto.builder().todoName("Todo 이름")
            .content("Todo 내용").build(), user);
        todo.setTodoId(todoId);

        given(todoRepository.findById(todoId)).willReturn(Optional.of(todo));

        boolean finish = true;

        // when
        TodoResponseDto responseDto = todoService.finishTodo(todoId, finish, user);

        // then
        assertEquals(finish, responseDto.isFinished());
    }
}