package com.sparta.mytodo.dto;

import com.sparta.mytodo.entity.TodoCard;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class TodoCardResponseDto {
    private String username;
    private String cardname;
    private String content;
    private boolean isfinished;
    private LocalDateTime cratedAt;
    private LocalDateTime modifiedAt;

    public TodoCardResponseDto(TodoCard todoCard) {
        this.username = todoCard.getUser().getUsername();
        this.cardname = todoCard.getCardname();
        this.content = todoCard.getContent();
        this.isfinished = todoCard.isIsfinished();
        this.cratedAt = todoCard.getCreatedAt();
        this.modifiedAt = todoCard.getModifiedAt();
    }
}
