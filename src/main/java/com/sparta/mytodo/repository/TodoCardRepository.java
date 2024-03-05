package com.sparta.mytodo.repository;

import com.sparta.mytodo.entity.TodoCard;
import com.sparta.mytodo.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TodoCardRepository extends JpaRepository<TodoCard, Long> {

    Optional<List<TodoCard>> findAllByUser(User user);

    Optional<List<TodoCard>> findAllByOrderByUser();

    @Query("select t from TodoCard t where t.user.userId = ?1 and t.finished = false")
    List<TodoCard> findAllByUserAndIsFinishedIsFalse(Long userId);
}
