package com.sparta.mytodo.domain.stomp;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @MessageMapping("/chat-rooms/{roomId}/messages")
    @SendTo("/topic/chat-rooms/{roomId}")
    public Message sendMessage(@DestinationVariable Long roomId, Message message) throws Exception {
        Thread.sleep(500); // 지연 시뮬레이션

        return messageService.createMessage(roomId, message);
    }

    @GetMapping("/api/messages/{roomId}")
    public List<Message> getRoomMessages(@PathVariable Long roomId) {
        return messageService.getRoomMessages(roomId);
    }
}