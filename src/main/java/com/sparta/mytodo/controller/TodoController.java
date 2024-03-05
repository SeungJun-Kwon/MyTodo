package com.sparta.mytodo.controller;

import com.sparta.mytodo.dto.TodoRequestDto;
import com.sparta.mytodo.dto.TodoResponseDto;
import com.sparta.mytodo.entity.ResponseDto;
import com.sparta.mytodo.security.UserDetailsImpl;
import com.sparta.mytodo.service.TodoService;
import com.sparta.mytodo.util.ValidationUtil;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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
                .httpCode(200)
                .data(responseDto).build()
        );
    }

    @GetMapping("/todos")
    public ResponseEntity<ResponseDto<List<TodoResponseDto>>> getTodos() {

        List<TodoResponseDto> responseDtoList = todoService.getTodos();

        return ResponseEntity.ok().body(
            ResponseDto.<List<TodoResponseDto>>builder()
                .httpCode(200)
                .data(responseDtoList).build()
        );
    }

    @GetMapping("/todos/user-id/{userId}")
    public ResponseEntity<ResponseDto<List<TodoResponseDto>>> getTodosByUser(
        @PathVariable Long userId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<TodoResponseDto> responseDtoList = todoService.getTodosByUser(userId,
            userDetails.getUser());

        return ResponseEntity.ok().body(
            ResponseDto.<List<TodoResponseDto>>builder()
                .httpCode(200)
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
                .httpCode(200)
                .data(responseDto).build()
        );
    }

    @PutMapping("todos/{todoId}")
    public ResponseEntity<ResponseDto<TodoResponseDto>> finishTodo(
        @PathVariable Long todoId,
        @RequestParam(name = "finished") boolean finished,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        TodoResponseDto responseDto = todoService.finishTodo(todoId, finished,
            userDetails.getUser());

        return ResponseEntity.ok().body(
            ResponseDto.<TodoResponseDto>builder()
                .httpCode(200)
                .data(responseDto).build()
        );
    }
}
