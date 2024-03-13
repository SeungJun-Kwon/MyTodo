package com.sparta.mytodo.domain.todo.dto;

import com.sparta.mytodo.domain.todo.entity.Todo;
import com.sparta.mytodo.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(exclude = {"createdAt", "modifiedAt"})
public class TodoResponseDto {

    private String userName;
    private String todoName;
    private String content;
    private boolean finished;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public TodoResponseDto(Todo todo, User user) {
        this.userName = user.getUserName();
        this.todoName = todo.getTodoName();
        this.content = todo.getContent();
        this.finished = todo.isFinished();
        this.createdAt = todo.getCreatedAt();
        this.modifiedAt = todo.getModifiedAt();
    }
}
