package com.sparta.mytodo.domain.stomp;

import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("select c from ChatRoom c left join Member m on c.chatRoomId = m.chatRoomId where ?1 = m.userId")
    List<ChatRoom> findAllByMember(Long userId);

    @Query("select c from ChatRoom c where c.chatRoomId not in (select m.chatRoomId from Member m where m.userId = ?1)")
    List<ChatRoom> findAllByNotInMember(Long userId);

    @Query("select c from ChatRoom c where c.chatRoomName like concat('%', :keyword, '%') or c.description like concat('%', :keyword, '%')")
    List<ChatRoom> findAllByKeyword(@Param("keyword") String keyword);

    @Query("select c from ChatRoom c where c.chatRoomTag like concat('%', :tag, '%')")
    List<ChatRoom> findAllByTag(@Param("tag") String tag);
}
