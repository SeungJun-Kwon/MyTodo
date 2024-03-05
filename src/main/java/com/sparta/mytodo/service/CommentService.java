package com.sparta.mytodo.service;

import com.sparta.mytodo.dto.CommentRequestDto;
import com.sparta.mytodo.dto.CommentResponseDto;
import com.sparta.mytodo.entity.Comment;
import com.sparta.mytodo.entity.Todo;
import com.sparta.mytodo.entity.User;
import com.sparta.mytodo.repository.CommentRepository;
import com.sparta.mytodo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;

    public CommentResponseDto createComment(Long cardId, CommentRequestDto requestDto, User user) {
        Todo todo = todoRepository.findById(cardId).orElseThrow(
            () -> new IllegalArgumentException("해당 카드가 존재하지 않습니다.")
        );

        Comment comment = new Comment(requestDto, user, todo);

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto,
        User user) {
        Comment comment = validateComment(commentId, user);

        comment.setContent(requestDto.getContent());

        return new CommentResponseDto(comment);
    }

    @Transactional
    public Long deleteComment(Long commentId, User user) {
        Comment comment = validateComment(commentId, user);

        commentRepository.deleteById(commentId);

        return commentId;
    }

    private Comment validateComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
            () -> new NullPointerException("존재하지 않는 댓글입니다.")
        );

        try {
            if (!user.getUserId().equals(comment.getUser().getUserId())) {
                throw new IllegalArgumentException("유저 정보가 일치하지 않습니다.");
            }
        } catch (NullPointerException e) {
            throw new NullPointerException("댓글에 유저 정보가 존재하지 않습니다.");
        }

        return comment;
    }
}
