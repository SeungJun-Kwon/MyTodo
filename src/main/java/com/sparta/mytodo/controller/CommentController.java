package com.sparta.mytodo.controller;

import com.sparta.mytodo.dto.CommentRequestDto;
import com.sparta.mytodo.dto.CommentResponseDto;
import com.sparta.mytodo.entity.ResponseMessage;
import com.sparta.mytodo.security.UserDetailsImpl;
import com.sparta.mytodo.service.CommentService;
import com.sparta.mytodo.util.ValidationUtil;
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

    @PostMapping("/comments/card-id/{cardId}")
    public ResponseEntity<ResponseMessage<?>> createComment(@PathVariable Long cardId,
        @Valid @RequestBody CommentRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        BindingResult bindingResult) {

        ValidationUtil.validateRequestDto(bindingResult);

        CommentResponseDto responseDto = commentService.createComment(cardId, requestDto,
            userDetails.getUser());

        return ResponseEntity.ok().body(
            ResponseMessage.builder()
                .msg(responseDto.getContent() + " 댓글 작성 성공!")
                .httpCode(200)
                .data(responseDto).build()
        );
    }

    @PutMapping("/comments/comment-id/{commentId}")
    public ResponseEntity<ResponseMessage<?>> updateComment(@PathVariable Long commentId,
        @Valid @RequestBody CommentRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        BindingResult bindingResult) {

        ValidationUtil.validateRequestDto(bindingResult);

        CommentResponseDto responseDto = commentService.updateComment(commentId, requestDto,
            userDetails.getUser());

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
        Long deleteId = commentService.deleteComment(commentId, userDetails.getUser());

        return ResponseEntity.ok().body(
            ResponseMessage.builder()
                .msg(commentId + " 댓글 삭제 성공!")
                .httpCode(200)
                .data(deleteId).build()
        );
    }
}
