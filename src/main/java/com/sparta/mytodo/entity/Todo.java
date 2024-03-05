package com.sparta.mytodo.entity;

import com.sparta.mytodo.dto.TodoRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "todo")
public class Todo extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoId;

    @Column(nullable = false)
    private String todoName;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean finished;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    public Todo(TodoRequestDto requestDto, User user) {
        this.todoName = requestDto.getTodoName();
        this.content = requestDto.getContent();
        this.finished = requestDto.isFinished();
        this.user = user;
    }
}