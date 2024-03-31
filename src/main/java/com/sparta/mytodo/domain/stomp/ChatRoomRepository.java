package com.sparta.mytodo.domain.stomp;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("select c from ChatRoom c left join Member m on c.chatRoomId = m.chatRoomId where ?1 = m.userId")
    List<ChatRoom> findAllByMember(Long userId);
}
