package com.sparta.mytodo.service;

import com.sparta.mytodo.dto.TodoCardRequestDto;
import com.sparta.mytodo.dto.TodoCardResponseDto;
import com.sparta.mytodo.entity.TodoCard;
import com.sparta.mytodo.entity.User;
import com.sparta.mytodo.repository.TodoCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoCardService {
    private final TodoCardRepository todoCardRepository;

    public TodoCardResponseDto createCard(TodoCardRequestDto requestDto, User user) {
        TodoCard todoCard = new TodoCard(requestDto, user);

        todoCardRepository.save(todoCard);

        return new TodoCardResponseDto(todoCard);
    }

    public List<TodoCardResponseDto> getCardsByUser(Long userId, User user) {
        if(!userId.equals(user.getId())) {
            throw new IllegalArgumentException("유저 정보가 일치하지 않습니다.");
        }

        List<TodoCard> todoCards = todoCardRepository.findAllByUser(user).orElseThrow(
                () -> new NullPointerException("카드가 존재하지 않습니다.")
        );

        return todoCards.stream().map(TodoCardResponseDto::new).toList();
    }

    public List<TodoCardResponseDto> getCards() {
        List<TodoCard> todoCards = todoCardRepository.findAllByOrderByUser().orElseThrow(
                () -> new NullPointerException("카드가 존재하지 않습니다.")
        );

        return todoCards.stream().map(TodoCardResponseDto::new).toList();
    }

    @Transactional
    public TodoCardResponseDto updateCard(Long cardId, TodoCardRequestDto requestDto, User user) {
        TodoCard todoCard = validateCard(cardId, user);

        todoCard.setCardname(requestDto.getCardname());
        todoCard.setContent(requestDto.getContent());

        return new TodoCardResponseDto(todoCard);
    }

    @Transactional
    public TodoCardResponseDto finishTodo(Long cardId, boolean isFinished, User user) {
        TodoCard todoCard = validateCard(cardId, user);

        todoCard.setIsfinished(isFinished);

        return new TodoCardResponseDto(todoCard);
    }

    private TodoCard validateCard(Long cardId, User user) {
        TodoCard todoCard = todoCardRepository.findById(cardId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 카드입니다.")
        );

        try {
            if (!user.getId().equals(todoCard.getUser().getId())) {
                throw new IllegalArgumentException("사용자 정보가 일치하지 않습니다.");
            }
        } catch (NullPointerException e) {
            throw new NullPointerException("카드에 사용자 정보가 존재하지 않습니다.");
        }

        return todoCard;
    }
}
