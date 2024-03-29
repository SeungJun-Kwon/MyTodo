package com.sparta.mytodo.domain.stomp;

import java.util.List;

public interface MessageRepositoryCustom {

    List<GetMessageResponse> findAllByRoomId(Long roomId);
}
