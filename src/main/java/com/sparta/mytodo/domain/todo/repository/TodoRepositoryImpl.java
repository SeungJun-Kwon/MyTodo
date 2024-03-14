package com.sparta.mytodo.domain.todo.repository;

import static com.sparta.mytodo.domain.todo.entity.QTodo.todo;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.mytodo.domain.todo.entity.Todo;
import com.sparta.mytodo.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Todo> findAllByUser(User user, Pageable pageable) {
        List<Todo> content = queryFactory.selectFrom(todo).where(todo.user.eq(user))
            .offset(pageable.getOffset()) // 페이지 번호
            .limit(pageable.getPageSize()) // 페이지 사이즈
            .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(todo.count()).from(todo)
            .where(todo.user.eq(user));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
