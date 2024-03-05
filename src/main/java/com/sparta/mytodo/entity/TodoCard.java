package com.sparta.mytodo.entity;

import com.sparta.mytodo.dto.TodoCardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "card")
public class TodoCard extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @Column(nullable = false)
    private String cardName;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean finished;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    public TodoCard(TodoCardRequestDto requestDto, User user) {
        this.cardName = requestDto.getCardName();
        this.content = requestDto.getContent();
        this.finished = requestDto.isFinished();
        this.user = user;
    }
}