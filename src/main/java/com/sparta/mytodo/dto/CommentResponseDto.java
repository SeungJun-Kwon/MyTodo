package com.sparta.mytodo.dto;

import com.sparta.mytodo.entity.Comment;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private String username;
    private String cardname;
    private String content;
    private LocalDateTime cratedAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.username = comment.getUser().getUsername();
        this.cardname = comment.getTodoCard().getCardname();
        this.content = comment.getContent();
        this.cratedAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
