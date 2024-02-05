package com.sparta.mytodo.dto;

import com.sparta.mytodo.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto {
    private String content;
    private LocalDateTime cratedAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.content = comment.getContent();
        this.cratedAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
