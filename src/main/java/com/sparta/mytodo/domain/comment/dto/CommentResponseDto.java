package com.sparta.mytodo.domain.comment.dto;

import com.sparta.mytodo.domain.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private String userName;
    private String todoName;
    private String content;
    private LocalDateTime cratedAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.userName = comment.getUser().getUserName();
        this.todoName = comment.getTodo().getTodoName();
        this.content = comment.getContent();
        this.cratedAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
