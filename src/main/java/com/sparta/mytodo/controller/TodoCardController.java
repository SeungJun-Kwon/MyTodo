package com.sparta.mytodo.controller;

import com.sparta.mytodo.dto.TodoCardRequestDto;
import com.sparta.mytodo.dto.TodoCardResponseDto;
import com.sparta.mytodo.entity.ResponseMessage;
import com.sparta.mytodo.security.UserDetailsImpl;
import com.sparta.mytodo.service.TodoCardService;
import com.sparta.mytodo.util.ValidationUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class TodoCardController {
    private final TodoCardService todoCardService;

    @PostMapping("/cards")
    public ResponseEntity<ResponseMessage<?>> createCard(@RequestBody TodoCardRequestDto requestDto,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                         BindingResult bindingResult) {
        TodoCardResponseDto responseDto;
        try {
            // Validation 예외처리
            List<String> errorMessages = ValidationUtil.getErrors(bindingResult);
            if(errorMessages != null && !errorMessages.isEmpty()) {
                return ResponseEntity.badRequest().
                        body(ResponseMessage.builder()
                                .msg(errorMessages.toString())
                                .httpCode(HttpStatus.BAD_REQUEST.value())
                                .data(null)
                                .build());
            }

            responseDto = todoCardService.createCard(requestDto, userDetails.getUser());
        } catch (NullPointerException | IllegalArgumentException e) {
            return ResponseEntity.ok().body(
                    ResponseMessage.builder()
                            .msg(e.getMessage())
                            .httpCode(400)
                            .data(null).build()
            );
        }

        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .msg("카드 작성 성공!")
                        .httpCode(200)
                        .data(responseDto).build()
        );
    }

    @GetMapping("/cards")
    public ResponseEntity<ResponseMessage<?>> getCards() {
        List<TodoCardResponseDto> responseDtoList;

        try {
            responseDtoList = todoCardService.getCards();
        } catch (NullPointerException | IllegalArgumentException e) {
            return ResponseEntity.ok().body(
                    ResponseMessage.builder()
                            .msg(e.getMessage())
                            .httpCode(400)
                            .data(null).build()
            );
        }

        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .msg("카드 전체 조회 성공!")
                        .httpCode(200)
                        .data(responseDtoList).build()
        );
    }

    @GetMapping("/cards/user-id/{userId}")
    public ResponseEntity<ResponseMessage<?>> getCardsByUser(@PathVariable Long userId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<TodoCardResponseDto> responseDtoList;

        try {
            responseDtoList = todoCardService.getCardsByUser(userId, userDetails.getUser());
        } catch (NullPointerException | IllegalArgumentException e) {
            return ResponseEntity.ok().body(
                    ResponseMessage.builder()
                            .msg(e.getMessage())
                            .httpCode(400)
                            .data(null).build()
            );
        }

        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .msg(userDetails.getUsername() + " 회원 카드 조회 성공!")
                        .httpCode(200)
                        .data(responseDtoList).build()
        );
    }

    @PutMapping("cards/card-id/{cardId}")
    public ResponseEntity<ResponseMessage<?>> updateCard(@PathVariable Long cardId,
                                                         @RequestBody TodoCardRequestDto requestDto,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                         BindingResult bindingResult) {
        TodoCardResponseDto responseDto;

        try{
            // Validation 예외처리
            List<String> errorMessages = ValidationUtil.getErrors(bindingResult);
            if(errorMessages != null && !errorMessages.isEmpty()) {
                return ResponseEntity.badRequest().
                        body(ResponseMessage.builder()
                                .msg(errorMessages.toString())
                                .httpCode(HttpStatus.BAD_REQUEST.value())
                                .data(null)
                                .build());
            }

            responseDto = todoCardService.updateCard(cardId, requestDto, userDetails.getUser());
        } catch (IllegalArgumentException | NullPointerException e) {
            return ResponseEntity.ok().body(
                    ResponseMessage.builder()
                            .msg(e.getMessage())
                            .httpCode(400)
                            .data(null).build()
            );
        }

        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .msg(userDetails.getUsername() + " 회원의 " + cardId + " 카드 수정 성공!")
                        .httpCode(200)
                        .data(responseDto).build()
        );
    }

    @PutMapping("cards/card-id/{cardId}/is-finished/{isFinished}")
    public ResponseEntity<ResponseMessage<?>> finishTodo(@PathVariable Long cardId,
                                                         @PathVariable boolean isFinished,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                         BindingResult bindingResult) {
        TodoCardResponseDto responseDto;

        try {
            // Validation 예외처리
            List<String> errorMessages = ValidationUtil.getErrors(bindingResult);
            if(errorMessages != null && !errorMessages.isEmpty()) {
                return ResponseEntity.badRequest().
                        body(ResponseMessage.builder()
                                .msg(errorMessages.toString())
                                .httpCode(HttpStatus.BAD_REQUEST.value())
                                .data(null)
                                .build());
            }

            responseDto = todoCardService.finishTodo(cardId, isFinished, userDetails.getUser());
        } catch (IllegalArgumentException | NullPointerException e) {
            return ResponseEntity.ok().body(
                    ResponseMessage.builder()
                            .msg(e.getMessage())
                            .httpCode(400)
                            .data(null).build()
            );
        }

        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .msg(userDetails.getUsername() + " 회원의 " + cardId + " 카드 완료!")
                        .httpCode(200)
                        .data(responseDto).build()
        );
    }
}
