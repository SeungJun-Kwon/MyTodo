package com.sparta.mytodo.repository;

import com.sparta.mytodo.entity.Todo;
import com.sparta.mytodo.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    Optional<List<Todo>> findAllByUser(User user);

    Optional<List<Todo>> findAllByOrderByUser();

    @Query("select t from Todo t where t.user.userId = ?1 and t.finished = false")
    List<Todo> findAllByUserAndIsFinishedIsFalse(Long userId);
}
