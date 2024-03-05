package com.sparta.mytodo.dto;

import com.sparta.mytodo.entity.TodoCard;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class TodoCardResponseDto {
    private String username;
    private String cardName;
    private String content;
    private boolean finished;
    private LocalDateTime cratedAt;
    private LocalDateTime modifiedAt;

    public TodoCardResponseDto(TodoCard todoCard) {
        this.username = todoCard.getUser().getUsername();
        this.cardName = todoCard.getCardName();
        this.content = todoCard.getContent();
        this.finished = todoCard.isFinished();
        this.cratedAt = todoCard.getCreatedAt();
        this.modifiedAt = todoCard.getModifiedAt();
    }
}
