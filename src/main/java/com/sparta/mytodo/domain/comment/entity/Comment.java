package com.sparta.mytodo.domain.comment.entity;

import com.sparta.mytodo.domain.comment.dto.CommentRequestDto;
import com.sparta.mytodo.domain.todo.entity.Todo;
import com.sparta.mytodo.domain.user.entity.User;
import com.sparta.mytodo.global.entity.Timestamped;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COMMENT_TB", indexes = {
    @Index(name = "idx_comment_todo_id", columnList = "todo_id"),
    @Index(name = "idx_comment_user_id", columnList = "user_id")})
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Todo todo;

    public Comment(CommentRequestDto requestDto, User user, Todo todo) {
        this.content = requestDto.getContent();
        this.user = user;
        this.todo = todo;
    }
}
