package com.sparta.mytodo.controller;

import com.sparta.mytodo.dto.TodoCardRequestDto;
import com.sparta.mytodo.dto.TodoCardResponseDto;
import com.sparta.mytodo.security.UserDetailsImpl;
import com.sparta.mytodo.service.TodoCardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class TodoCardController {
    private final TodoCardService todoCardService;

    @PostMapping("/card")
    public TodoCardResponseDto createCard(@RequestBody TodoCardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return todoCardService.createCard(requestDto, userDetails.getUser());
    }

    @GetMapping("/card/{id}")
    public List<TodoCardResponseDto> getCards(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return todoCardService.getCards(id, userDetails.getUser());
    }
}
