package com.sparta.mytodo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

import com.sparta.mytodo.dto.TodoCardRequestDto;
import com.sparta.mytodo.dto.TodoCardResponseDto;
import com.sparta.mytodo.entity.TodoCard;
import com.sparta.mytodo.entity.User;
import com.sparta.mytodo.entity.UserRoleEnum;
import com.sparta.mytodo.repository.TodoCardRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TodoCardServiceTest {

    @Mock
    TodoCardRepository todoCardRepository;

    TodoCardService todoCardService;

    @BeforeEach
    void setUp() {
        todoCardService = new TodoCardService(todoCardRepository);
    }

    @Test
    @DisplayName("Create Card")
    void createCard() {
        // given
        User user = new User("abc123", "abc12345", UserRoleEnum.USER);
        String cardname = "카드 이름";
        String content = "카드 내용";
        TodoCardRequestDto requestDto = TodoCardRequestDto.builder().cardname(cardname)
            .content(content).build();

        // when
        TodoCardResponseDto responseDto = todoCardService.createCard(requestDto, user);

        // then
        assertNotNull(responseDto);
        assertEquals(cardname, responseDto.getCardname());
        assertEquals(content, responseDto.getContent());
    }

    @Test
    @DisplayName("Get Cards By User")
    void getCardsByUser() {
        // given
        Long userId = 100L;
        User user = new User("abc123", "abc12345", UserRoleEnum.USER);
        user.setId(userId);

        TodoCard todoCard1 = new TodoCard(TodoCardRequestDto.builder().cardname("카드 이름1")
            .content("카드 내용1").build(), user);
        TodoCard todoCard2 = new TodoCard(TodoCardRequestDto.builder().cardname("카드 이름2")
            .content("카드 내용2").build(), user);

        List<TodoCard> todoCardList = new ArrayList<>();
        todoCardList.add(todoCard1);
        todoCardList.add(todoCard2);

        given(todoCardRepository.findAllByUser(user)).willReturn(Optional.of(todoCardList));

        // when
        List<TodoCardResponseDto> findTodoCardDtoList = todoCardService.getCardsByUser(userId,
            user);

        List<TodoCard> findTodoCardList = new ArrayList<>();
        for (TodoCardResponseDto dto : findTodoCardDtoList) {
            findTodoCardList.add(new TodoCard(
                TodoCardRequestDto.builder().cardname(dto.getCardname()).content(dto.getContent())
                    .build(), user));
        }

        System.out.println("Find Cards = " + findTodoCardList.get(0).getCardname());
        System.out.println("Find Cards = " + findTodoCardList.get(1).getCardname());

        // then
        assertEquals(findTodoCardList.get(0).getCardname(), todoCard1.getCardname());
        assertEquals(findTodoCardList.get(1).getCardname(), todoCard2.getCardname());
    }

    @Test
    @DisplayName("Get Cards")
    void getCards() {
        // given
        Long userId = 100L;
        User user = new User("abc123", "abc12345", UserRoleEnum.USER);
        user.setId(userId);

        TodoCard todoCard1 = new TodoCard(TodoCardRequestDto.builder().cardname("카드 이름1")
            .content("카드 내용1").build(), user);
        TodoCard todoCard2 = new TodoCard(TodoCardRequestDto.builder().cardname("카드 이름2")
            .content("카드 내용2").build(), user);

        List<TodoCard> todoCardList = new ArrayList<>();
        todoCardList.add(todoCard1);
        todoCardList.add(todoCard2);

        given(todoCardRepository.findAllByOrderByUser()).willReturn(Optional.of(todoCardList));

        // when
        List<TodoCardResponseDto> findTodoCardDtoList = todoCardService.getCards();

        List<TodoCard> findTodoCardList = new ArrayList<>();

        for (TodoCardResponseDto dto : findTodoCardDtoList) {
            findTodoCardList.add(new TodoCard(
                TodoCardRequestDto.builder().cardname(dto.getCardname()).content(dto.getContent())
                    .build(), user));
        }

        System.out.println("Find Cards = " + findTodoCardList.get(0).getCardname());
        System.out.println("Find Cards = " + findTodoCardList.get(1).getCardname());

        // then
        assertEquals(findTodoCardList.get(0).getCardname(), todoCard1.getCardname());
        assertEquals(findTodoCardList.get(1).getCardname(), todoCard2.getCardname());
    }

    @Test
    @DisplayName("Update Card")
    void updateCard() {
        // given
        Long userId = 100L;
        User user = new User("abc123", "abc12345", UserRoleEnum.USER);
        user.setId(userId);

        Long cardId = 100L;
        TodoCard todoCard = new TodoCard(TodoCardRequestDto.builder().cardname("카드 이름")
            .content("카드 내용").build(), user);
        todoCard.setId(cardId);

        given(todoCardRepository.findById(cardId)).willReturn(Optional.of(todoCard));

        String cardname = "카드 이름 수정";
        String content = "카드 내용 수정";

        // when
        TodoCardResponseDto responseDto = todoCardService.updateCard(cardId,
            TodoCardRequestDto.builder().cardname(cardname).content(content).build(), user);

        // then
        assertEquals(cardname, responseDto.getCardname());
        assertEquals(content, responseDto.getContent());
    }

    @Test
    @DisplayName("Finish Todo")
    void finishTodo() {
        // given
        Long userId = 100L;
        User user = new User("abc123", "abc12345", UserRoleEnum.USER);
        user.setId(userId);

        Long cardId = 100L;
        TodoCard todoCard = new TodoCard(TodoCardRequestDto.builder().cardname("카드 이름")
            .content("카드 내용").build(), user);
        todoCard.setId(cardId);

        given(todoCardRepository.findById(cardId)).willReturn(Optional.of(todoCard));

        boolean finish = true;

        // when
        TodoCardResponseDto responseDto = todoCardService.finishTodo(cardId, finish, user);

        // then
        assertEquals(finish, responseDto.isIsfinished());
    }
}