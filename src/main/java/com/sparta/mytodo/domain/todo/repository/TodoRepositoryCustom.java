package com.sparta.mytodo.domain.todo.repository;

import com.sparta.mytodo.domain.todo.entity.Todo;
import com.sparta.mytodo.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoRepositoryCustom {

    Page<Todo> findAllByUser(User user, Pageable pageable);
}
