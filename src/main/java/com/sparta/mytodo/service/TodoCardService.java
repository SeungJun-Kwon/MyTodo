package com.sparta.mytodo.service;

import com.sparta.mytodo.dto.TodoCardRequestDto;
import com.sparta.mytodo.dto.TodoCardResponseDto;
import com.sparta.mytodo.entity.TodoCard;
import com.sparta.mytodo.entity.User;
import com.sparta.mytodo.repository.TodoCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    public List<TodoCardResponseDto> getCards(Long userId, User user) {
        if(!userId.equals(user.getId())) {
            throw new IllegalArgumentException("유저 정보가 일치하지 않습니다.");
        }

        List<TodoCard> todoCards = todoCardRepository.findAllByUser(user).orElseThrow(
                () -> new NullPointerException("카드가 존재하지 않습니다.")
        );

        return todoCards.stream().map(TodoCardResponseDto::new).toList();
    }
}
