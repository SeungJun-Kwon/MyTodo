package com.sparta.mytodo.domain.stomp;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("select m from Message m where m.roomId = ?1")
    List<Message> findAllByRoomId(Long roomId);
}
