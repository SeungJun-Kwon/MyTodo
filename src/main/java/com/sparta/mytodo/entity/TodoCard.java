package com.sparta.mytodo.entity;

import com.sparta.mytodo.dto.TodoCardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "cards")
public class TodoCard extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cardname;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean isFinish;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public TodoCard(TodoCardRequestDto requestDto, User user) {
        this.cardname = requestDto.getCardname();
        this.content = requestDto.getContent();
        this.user = user;
    }
}