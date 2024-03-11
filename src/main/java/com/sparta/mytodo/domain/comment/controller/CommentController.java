package com.sparta.mytodo.domain.comment.controller;

import com.sparta.mytodo.domain.comment.dto.CommentRequestDto;
import com.sparta.mytodo.domain.comment.dto.CommentResponseDto;
import com.sparta.mytodo.global.dto.ResponseDto;
import com.sparta.mytodo.global.security.UserDetailsImpl;
import com.sparta.mytodo.domain.comment.service.CommentService;
import com.sparta.mytodo.global.util.ValidationUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments/todo-id/{todoId}")
    public ResponseEntity<ResponseDto<CommentResponseDto>> createComment(@PathVariable Long todoId,
        @Valid @RequestBody CommentRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        BindingResult bindingResult) {

        ValidationUtil.validateRequestDto(bindingResult);

        CommentResponseDto responseDto = commentService.createComment(todoId, requestDto,
            userDetails.getUser());

        return ResponseEntity.ok().body(
            ResponseDto.<CommentResponseDto>builder()
                .httpCode(200)
                .data(responseDto).build()
        );
    }

    @PutMapping("/comments/comment-id/{commentId}")
    public ResponseEntity<ResponseDto<CommentResponseDto>> updateComment(
        @PathVariable Long commentId,
        @Valid @RequestBody CommentRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        BindingResult bindingResult) {

        ValidationUtil.validateRequestDto(bindingResult);

        CommentResponseDto responseDto = commentService.updateComment(commentId, requestDto,
            userDetails.getUser());

        return ResponseEntity.ok().body(
            ResponseDto.<CommentResponseDto>builder()
                .httpCode(200)
                .data(responseDto).build()
        );
    }

    @DeleteMapping("/comments/comment-id/{commentId}")
    public ResponseEntity<ResponseDto<Long>> deleteComment(
        @PathVariable Long commentId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long deleteId = commentService.deleteComment(commentId, userDetails.getUser());

        return ResponseEntity.ok().body(
            ResponseDto.<Long>builder()
                .httpCode(200)
                .data(deleteId).build()
        );
    }
}
