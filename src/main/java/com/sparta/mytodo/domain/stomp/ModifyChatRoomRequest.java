package com.sparta.mytodo.domain.stomp;

import java.util.List;
import lombok.Getter;

@Getter
public class ModifyChatRoomRequest {

    private String chatRoomName;
    private String description;
    private String coverImage;
    private List<String> chatRoomTags;
}
