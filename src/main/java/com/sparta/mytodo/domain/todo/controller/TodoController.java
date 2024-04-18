package com.sparta.mytodo.domain.todo.controller;

import com.sparta.mytodo.domain.todo.dto.TodoRequestDto;
import com.sparta.mytodo.domain.todo.dto.TodoResponseDto;
import com.sparta.mytodo.domain.todo.service.TodoService;
import com.sparta.mytodo.global.dto.ResponseDto;
import com.sparta.mytodo.global.security.UserDetailsImpl;
import com.sparta.mytodo.global.util.ValidationUtil;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/todos")
    public ResponseEntity<ResponseDto<TodoResponseDto>> createTodo(
        @Valid @RequestBody TodoRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        BindingResult bindingResult) {

        ValidationUtil.validateRequestDto(bindingResult);

        TodoResponseDto responseDto = todoService.createTodo(requestDto,
            userDetails.getUser());

        return ResponseEntity.ok().body(
            ResponseDto.<TodoResponseDto>builder()
                .data(responseDto).build()
        );
    }

    @GetMapping("/todos")
    public ResponseEntity<ResponseDto<List<TodoResponseDto>>> getTodos() {

        List<TodoResponseDto> responseDtoList = todoService.getTodos();

        return ResponseEntity.ok().body(
            ResponseDto.<List<TodoResponseDto>>builder()
                .data(responseDtoList).build()
        );
    }

    @GetMapping("/todos/pages")
    public ResponseEntity<ResponseDto<Page<TodoResponseDto>>> getTodosPaging(
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam boolean isAsc,
        @RequestParam String sortBy
    ) {

        Page<TodoResponseDto> responseDtoPage = todoService.getTodosPaging(page - 1, size, isAsc,
            sortBy);

        return ResponseEntity.ok().body(
            ResponseDto.<Page<TodoResponseDto>>builder()
                .data(responseDtoPage).build()
        );
    }

    @GetMapping("/todos/user-id/{userId}")
    public ResponseEntity<ResponseDto<Page<TodoResponseDto>>> getTodosByUser(
        @PathVariable Long userId,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "true") boolean isAsc,
        @RequestParam(defaultValue = "createdAt") String sortBy,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Page<TodoResponseDto> responseDtoList = todoService.getTodosByUser(userId, page - 1, size,
            isAsc, sortBy,
            userDetails.getUser());

        return ResponseEntity.ok().body(
            ResponseDto.<Page<TodoResponseDto>>builder()
                .data(responseDtoList).build()
        );
    }

    @PutMapping("todos/{todoId}")
    public ResponseEntity<ResponseDto<TodoResponseDto>> updateTodo(
        @PathVariable Long todoId,
        @Valid @RequestBody TodoRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        BindingResult bindingResult) {

        ValidationUtil.validateRequestDto(bindingResult);

        TodoResponseDto responseDto = todoService.updateTodo(todoId, requestDto,
            userDetails.getUser());

        return ResponseEntity.ok().body(
            ResponseDto.<TodoResponseDto>builder()
                .data(responseDto).build()
        );
    }

    @PatchMapping("todos/{todoId}")
    public ResponseEntity<ResponseDto<TodoResponseDto>> finishTodo(
        @PathVariable Long todoId,
        @RequestParam(name = "finished") boolean finished,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        TodoResponseDto responseDto = todoService.finishTodo(todoId, finished,
            userDetails.getUser());

        return ResponseEntity.ok().body(
            ResponseDto.ofData(responseDto)
        );
    }
}
