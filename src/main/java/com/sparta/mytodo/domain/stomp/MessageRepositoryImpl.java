package com.sparta.mytodo.domain.stomp;

import static com.sparta.mytodo.domain.stomp.QMessage.message;
import static com.sparta.mytodo.domain.user.entity.QUser.user;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MessageRepositoryImpl implements MessageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<GetMessageResponse> findAllByRoomId(Long roomId) {
        return queryFactory.select(
                Projections.fields(GetMessageResponse.class, user.userName, message.messageId,
                    message.content))
            .from(message)
            .where(message.roomId.eq(roomId))
            .leftJoin(user).on(message.userId.eq(user.userId))
            .fetch();
    }
}
