package com.sparta.mytodo.domain.todo.dto;

import com.sparta.mytodo.domain.todo.entity.Todo;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class TodoResponseDto {

    private String userName;
    private String todoName;
    private String content;
    private boolean finished;
    private LocalDateTime cratedAt;
    private LocalDateTime modifiedAt;

    public TodoResponseDto(Todo todo) {
        this.userName = todo.getUser().getUserName();
        this.todoName = todo.getTodoName();
        this.content = todo.getContent();
        this.finished = todo.isFinished();
        this.cratedAt = todo.getCreatedAt();
        this.modifiedAt = todo.getModifiedAt();
    }
}
