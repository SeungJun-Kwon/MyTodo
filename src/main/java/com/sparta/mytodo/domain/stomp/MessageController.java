package com.sparta.mytodo.domain.stomp;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @MessageMapping("/messages/{roomId}")
    @SendTo("/topic/chat-rooms/{roomId}")
    public Message sendMessage(@DestinationVariable Long roomId, Message message) throws Exception {
        Thread.sleep(500); // 지연 시뮬레이션

        return messageService.createMessage(roomId, message);
    }
}