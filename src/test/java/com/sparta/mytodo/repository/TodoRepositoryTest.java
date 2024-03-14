package com.sparta.mytodo.repository;

import static com.sparta.mytodo.domain.todo.entity.QTodo.todo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.mytodo.domain.todo.dto.TodoRequestDto;
import com.sparta.mytodo.domain.todo.entity.Todo;
import com.sparta.mytodo.domain.todo.repository.TodoRepository;
import com.sparta.mytodo.domain.user.entity.User;
import com.sparta.mytodo.domain.user.repository.UserRepository;
import com.sparta.mytodo.global.security.UserRoleEnum;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

public class TodoRepositoryTest extends RepositoryTest {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JPAQueryFactory queryFactory;

    private static User user;

    @BeforeAll
    static void beforeAll() {
        user = new User("abc123@naver.com", "abc123", "abc123", UserRoleEnum.USER);
    }

    @BeforeEach
    void setUp() {
        userRepository.save(user);
    }

    @Test
    @DisplayName("Todo 생성")
    void createTodo() {
        Todo todo = new Todo();
        String todoName = "Todo 이름";
        String content = "Todo 내용";
        boolean finished = false;
        todo.setTodoName(todoName);
        todo.setContent(content);
        todo.setFinished(finished);
        todo.setUser(user);

        Todo createdTodo = todoRepository.save(todo);

        assertNotNull(createdTodo);
        assertEquals(todoName, createdTodo.getTodoName());
        assertEquals(content, createdTodo.getContent());
        assertEquals(finished, createdTodo.isFinished());
    }

    @Test
    @DisplayName("Todo 수정")
    void updateTodo() {
        Todo todo = new Todo(
            TodoRequestDto.builder().todoName("Todo 이름").content("Todo 내용").build(),
            user);
        todoRepository.save(todo);

        String todoName = "Todo 이름 수정";
        String content = "내용 수정";
        boolean finished = true;
        if (todoRepository.findById(todo.getTodoId()).isPresent()) {
            todo.setTodoName(todoName);
            todo.setContent(content);
            todo.setFinished(finished);
        }

        assertEquals(todoName, todo.getTodoName());
        assertEquals(content, todo.getContent());
        assertEquals(finished, todo.isFinished());
    }

    @Test
    @DisplayName("Todo 삭제")
    void deleteTodo() {
        Todo todo = new Todo(
            TodoRequestDto.builder().todoName("Todo 이름").content("Todo 내용").build(),
            user);
        todoRepository.save(todo);

        if (todoRepository.findById(todo.getTodoId()).isPresent()) {
            todoRepository.delete(todo);
        }
        todo = todoRepository.findById(todo.getTodoId()).orElse(null);

        assertNull(todo);
    }

    @Test
    void findAllByUserAndIsFinishedIsFalse() {
        Todo todo = new Todo(
            TodoRequestDto.builder().todoName("Todo 이름").content("Todo 내용").build(),
            user);
        todoRepository.save(todo);

        List<Todo> todoList = todoRepository.findAllByUserAndIsFinishedIsFalse(
            user.getUserId());

        assertNotNull(todoList);
    }

    @Nested
    @DisplayName("페이징 Count 쿼리 시간 테스트")
    class 페이징시간테스트 {

        private User user;
        private List<Todo> todoList;

        @BeforeEach
        void setup() {
            user = new User("abc123@naver.com", "abc123", "abc12345", UserRoleEnum.USER);
            user.setUserId(100L);

            todoList = createTodoList(999, user); // 999개의 Todo 생성
            todoRepository.saveAll(todoList);
        }

        @Test
        @DisplayName("무조건 Count 쿼리 날리기")
        void 무조건Count쿼리날리기() {
            // given
            int page = 9;
            int size = 100;
            Pageable pageable = PageRequest.of(page, size);

            // when
            long startTime = System.currentTimeMillis();
            Page<Todo> result = findAllByUser1(user, pageable);
            long endTime = System.currentTimeMillis();
            long time = endTime - startTime;

            // then
            System.out.println("Count 쿼리 포함 시간 : " + time + "ms");
        }

        @Test
        @DisplayName("필요할 때만 Count 쿼리 날리기")
        void 필요할때만Count쿼리날리기() {
            // given
            int page = 9;
            int size = 100;
            Pageable pageable = PageRequest.of(page, size);

            // when
            long startTime = System.currentTimeMillis();
            Page<Todo> result = findAllByUser2(user, pageable);
            long endTime = System.currentTimeMillis();
            long time = endTime - startTime;

            // then
            System.out.println("Count 쿼리 미포함 시간 : " + time + "ms");
        }

        private Page<Todo> findAllByUser1(User user, Pageable pageable) {
            List<Todo> content = queryFactory.selectFrom(todo).where(todo.user.eq(user))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

            Long count = queryFactory.select(todo.count()).from(todo)
                .where(todo.user.eq(user)).fetchOne();

            return new PageImpl<>(content, pageable, count);
        }

        private Page<Todo> findAllByUser2(User user, Pageable pageable) {
            List<Todo> content = queryFactory.selectFrom(todo).where(todo.user.eq(user))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

            JPAQuery<Long> countQuery = queryFactory.select(todo.count()).from(todo)
                .where(todo.user.eq(user));

            return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
        }

        private List<Todo> createTodoList(int count, User user) {
            List<Todo> todoList = new ArrayList<>();
            for (int i = 1; i <= count; i++) {
                Todo todo = new Todo(
                    TodoRequestDto.builder().todoName("Todo 이름" + i).content("Todo 내용" + i).build(),
                    user);
                todoList.add(todo);
            }
            return todoList;
        }
    }
}
