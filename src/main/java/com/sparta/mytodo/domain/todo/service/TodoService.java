package com.sparta.mytodo.domain.todo.service;

import com.sparta.mytodo.domain.todo.dto.TodoRequestDto;
import com.sparta.mytodo.domain.todo.dto.TodoResponseDto;
import com.sparta.mytodo.domain.todo.entity.Todo;
import com.sparta.mytodo.domain.todo.repository.TodoRepository;
import com.sparta.mytodo.domain.user.entity.User;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoResponseDto createTodo(TodoRequestDto requestDto, User user) {
        Todo todo = new Todo(requestDto, user);

        todoRepository.save(todo);

        return new TodoResponseDto(todo, user);
    }

    public Page<TodoResponseDto> getTodosByUser(Long userId, int page, int size, boolean isAsc,
        String sortBy, User user) {
        if (!userId.equals(user.getUserId())) {
            throw new IllegalArgumentException("유저 정보가 일치하지 않습니다.");
        }

        // 페이징 처리
        Sort.Direction direction = isAsc ? Direction.ASC : Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Todo> todos = todoRepository.findAllByUser(user, pageable);

        return todos.map(todo -> new TodoResponseDto(todo, user));
    }

    public List<TodoResponseDto> getTodos() {
        List<Todo> todos = todoRepository.findAllByOrderByUser().orElseThrow(
            () -> new NullPointerException("Todo 가 존재하지 않습니다.")
        );

        return todos.stream().map(todo -> new TodoResponseDto(todo, todo.getUser())).toList();
    }

    @Transactional
    public TodoResponseDto updateTodo(Long cardId, TodoRequestDto requestDto, User user) {
        Todo todo = validateTodo(cardId, user);

        todo.setTodoName(requestDto.getTodoName());
        todo.setContent(requestDto.getContent());

        return new TodoResponseDto(todo, user);
    }

    @Transactional
    public TodoResponseDto finishTodo(Long todoId, boolean finished, User user) {
        Todo todo = validateTodo(todoId, user);

        todo.setFinished(finished);

        return new TodoResponseDto(todo, user);
    }

    private Todo validateTodo(Long todoId, User user) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
            () -> new NullPointerException("존재하지 않는 카드입니다.")
        );

        try {
            if (!user.getUserId().equals(todo.getUser().getUserId())) {
                throw new IllegalArgumentException("사용자 정보가 일치하지 않습니다.");
            }
        } catch (NullPointerException e) {
            throw new NullPointerException("카드에 사용자 정보가 존재하지 않습니다.");
        }

        return todo;
    }

    public Page<TodoResponseDto> getTodosPaging(int page, int size, boolean isAsc, String sortBy) {

        // 페이징 처리
        Sort.Direction direction = isAsc ? Direction.ASC : Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        // 가져오기
        Page<Todo> todoPage = todoRepository.findAll(pageable);

        return todoPage.map(todo -> new TodoResponseDto(todo, todo.getUser()));
    }
}
