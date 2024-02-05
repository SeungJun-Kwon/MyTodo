package com.sparta.mytodo.dto;

import com.sparta.mytodo.entity.TodoCard;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TodoCardResponseDto {
    private String cardname;
    private String content;
    private boolean isfinished;
    private LocalDateTime cratedAt;
    private LocalDateTime modifiedAt;

    public TodoCardResponseDto(TodoCard todoCard) {
        this.cardname = todoCard.getCardname();
        this.content = todoCard.getContent();
        this.isfinished = todoCard.isIsfinished();
        this.cratedAt = todoCard.getCreatedAt();
        this.modifiedAt = todoCard.getModifiedAt();
    }
}
