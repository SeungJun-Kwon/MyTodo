package com.sparta.mytodo.domain.comment.dto;

import com.sparta.mytodo.domain.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(exclude = {"createdAt", "modifiedAt"})
public class CommentResponseDto {

    private String userName;
    private String todoName;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.userName = comment.getUser().getUserName();
        this.todoName = comment.getTodo().getTodoName();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
