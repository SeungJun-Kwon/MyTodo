package com.sparta.mytodo.controller;

import com.sparta.mytodo.dto.TodoCardRequestDto;
import com.sparta.mytodo.dto.TodoCardResponseDto;
import com.sparta.mytodo.entity.ResponseMessage;
import com.sparta.mytodo.security.UserDetailsImpl;
import com.sparta.mytodo.service.TodoCardService;
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
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class TodoCardController {

    private final TodoCardService todoCardService;

    @PostMapping("/cards")
    public ResponseEntity<ResponseMessage<TodoCardResponseDto>> createCard(
        @Valid @RequestBody TodoCardRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        BindingResult bindingResult) {

        ValidationUtil.validateRequestDto(bindingResult);

        TodoCardResponseDto responseDto = todoCardService.createCard(requestDto,
            userDetails.getUser());

        return ResponseEntity.ok().body(
            ResponseMessage.<TodoCardResponseDto>builder()
                .msg("카드 작성 성공!")
                .httpCode(200)
                .data(responseDto).build()
        );
    }

    @GetMapping("/cards")
    public ResponseEntity<ResponseMessage<List<TodoCardResponseDto>>> getCards() {

        List<TodoCardResponseDto> responseDtoList = todoCardService.getCards();

        return ResponseEntity.ok().body(
            ResponseMessage.<List<TodoCardResponseDto>>builder()
                .msg("카드 전체 조회 성공!")
                .httpCode(200)
                .data(responseDtoList).build()
        );
    }

    @GetMapping("/cards/user-id/{userId}")
    public ResponseEntity<ResponseMessage<List<TodoCardResponseDto>>> getCardsByUser(
        @PathVariable Long userId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<TodoCardResponseDto> responseDtoList = todoCardService.getCardsByUser(userId,
            userDetails.getUser());

        return ResponseEntity.ok().body(
            ResponseMessage.<List<TodoCardResponseDto>>builder()
                .msg(userDetails.getUsername() + " 회원 카드 조회 성공!")
                .httpCode(200)
                .data(responseDtoList).build()
        );
    }

    @PutMapping("cards/card-id/{cardId}")
    public ResponseEntity<ResponseMessage<TodoCardResponseDto>> updateCard(
        @PathVariable Long cardId,
        @Valid @RequestBody TodoCardRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        BindingResult bindingResult) {

        ValidationUtil.validateRequestDto(bindingResult);

        TodoCardResponseDto responseDto = todoCardService.updateCard(cardId, requestDto,
            userDetails.getUser());

        return ResponseEntity.ok().body(
            ResponseMessage.<TodoCardResponseDto>builder()
                .msg(userDetails.getUsername() + " 회원의 " + cardId + " 카드 수정 성공!")
                .httpCode(200)
                .data(responseDto).build()
        );
    }

    @PutMapping("cards/card-id/{cardId}/is-finished/{isFinished}")
    public ResponseEntity<ResponseMessage<TodoCardResponseDto>> finishTodo(
        @PathVariable Long cardId,
        @PathVariable boolean isFinished,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        TodoCardResponseDto responseDto = todoCardService.finishTodo(cardId, isFinished,
            userDetails.getUser());

        return ResponseEntity.ok().body(
            ResponseMessage.<TodoCardResponseDto>builder()
                .msg(userDetails.getUsername() + " 회원의 " + cardId + " 카드 완료!")
                .httpCode(200)
                .data(responseDto).build()
        );
    }
}
