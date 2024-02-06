package com.sparta.mytodo.controller;

import com.sparta.mytodo.dto.CommentRequestDto;
import com.sparta.mytodo.dto.CommentResponseDto;
import com.sparta.mytodo.entity.ResponseMessage;
import com.sparta.mytodo.security.UserDetailsImpl;
import com.sparta.mytodo.service.CommentService;
import com.sparta.mytodo.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comments/card-id/{cardId}")
    public ResponseEntity<ResponseMessage<?>> createComment(@PathVariable Long cardId,
                                                            @RequestBody CommentRequestDto requestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                            BindingResult bindingResult) {
        CommentResponseDto responseDto;

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

            responseDto = commentService.createComment(cardId, requestDto, userDetails.getUser());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok().body(
                    ResponseMessage.builder()
                            .msg(e.getMessage())
                            .httpCode(400)
                            .data(null).build()
            );
        }

        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .msg(responseDto.getContent() + " 댓글 작성 성공!")
                        .httpCode(200)
                        .data(responseDto).build()
        );
    }

    @PutMapping("/comments/comment-id/{commentId}")
    public ResponseEntity<ResponseMessage<?>> updateComment(@PathVariable Long commentId,
                                                            @RequestBody CommentRequestDto requestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                            BindingResult bindingResult) {
        CommentResponseDto responseDto;

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

            responseDto = commentService.updateComment(commentId, requestDto, userDetails.getUser());
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
                        .msg(responseDto.getContent() + " 댓글 수정 성공!")
                        .httpCode(200)
                        .data(responseDto).build()
        );
    }

    @DeleteMapping("/comments/comment-id/{commentId}")
    public ResponseEntity<ResponseMessage<?>> deleteComment(@PathVariable Long commentId,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long deleteId;

        try {
            deleteId = commentService.deleteComment(commentId, userDetails.getUser());
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
                        .msg(commentId + " 댓글 삭제 성공!")
                        .httpCode(200)
                        .data(deleteId).build()
        );
    }
}
