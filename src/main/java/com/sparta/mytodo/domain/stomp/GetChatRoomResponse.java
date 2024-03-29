package com.sparta.mytodo.domain.stomp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetChatRoomResponse {

    private String userName;
    private Long chatRoomId;
    private String chatRoomName;
    private String description;
    private String coverImage;

    public GetChatRoomResponse(String userName, ChatRoom chatRoom) {
        this.userName = userName;
        chatRoomId = chatRoom.getChatRoomId();
        chatRoomName = chatRoom.getChatRoomName();
        description = chatRoom.getDescription();
        coverImage = chatRoom.getDescription();
    }
}
