package com.sparta.mytodo.repository;

import com.sparta.mytodo.entity.TodoCard;
import com.sparta.mytodo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoCardRepository extends JpaRepository<TodoCard, Long> {
    Optional<List<TodoCard>> findAllByUser(User user);

    Optional<List<TodoCard>> findAllByOrderByUser();
}
