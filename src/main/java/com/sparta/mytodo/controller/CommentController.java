package com.sparta.mytodo.controller;

import com.sparta.mytodo.dto.CommentRequestDto;
import com.sparta.mytodo.dto.CommentResponseDto;
import com.sparta.mytodo.security.UserDetailsImpl;
import com.sparta.mytodo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comments/card-id/{cardId}")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long cardId,
                                                           @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(commentService.createComment(cardId, requestDto, userDetails.getUser()));
    }
}
