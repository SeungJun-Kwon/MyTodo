package com.sparta.mytodo.domain.stomp;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @MessageMapping("/messages/{roomId}")
    @SendTo("/topic/chat-rooms/{roomId}")
    public Message sendMessage(@DestinationVariable Long roomId, Message message) throws Exception {
        Thread.sleep(500); // 지연 시뮬레이션
        return new Message(message.getUserName(), message.getContent());
    }
}