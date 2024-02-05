package com.sparta.mytodo.service;

import com.sparta.mytodo.dto.CommentRequestDto;
import com.sparta.mytodo.dto.CommentResponseDto;
import com.sparta.mytodo.entity.Comment;
import com.sparta.mytodo.entity.TodoCard;
import com.sparta.mytodo.entity.User;
import com.sparta.mytodo.repository.CommentRepository;
import com.sparta.mytodo.repository.TodoCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TodoCardRepository todoCardRepository;

    public CommentResponseDto createComment(Long userId, Long cardId, CommentRequestDto requestDto, User user) {
        if(!userId.equals(user.getId())) {
            throw new IllegalArgumentException("유저 정보가 일치하지 않습니다.");
        }

        TodoCard todoCard = todoCardRepository.findById(cardId).orElseThrow(
                () -> new IllegalArgumentException("해당 카드가 존재하지 않습니다.")
        );

        Comment comment = new Comment(requestDto, user, todoCard);

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }
}
