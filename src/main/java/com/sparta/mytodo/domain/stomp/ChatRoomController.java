package com.sparta.mytodo.domain.stomp;

import com.sparta.mytodo.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/api/chat-rooms")
    public List<GetChatRoomResponse> getChatRooms(
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatRoomService.getChatRooms(userDetails.getUser());
    }

    @PostMapping("/api/chat-rooms")
    public GetChatRoomResponse createChatRoom(
        @RequestBody CreateChatRoomRequest request,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatRoomService.createChatRoom(request, userDetails.getUser());
    }

    @PostMapping("/api/chat-rooms/{chatRoomId}")
    public Long enterChatRoom(
        @PathVariable Long chatRoomId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatRoomService.enterChatRoom(chatRoomId, userDetails.getUser());
    }

    @GetMapping("/api/chat-rooms/my-rooms")
    public List<GetChatRoomResponse> getMyChatRooms(
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatRoomService.getMyChatRooms(userDetails.getUser());
    }

    @GetMapping("/api/chat-rooms/search-keyword")
    public List<GetChatRoomResponse> searchChatRoomsByKeyword(
        @RequestParam(name = "keyword") String keyword
    ) {
        return chatRoomService.searchChatRoomsByKeyword(keyword);
    }

    @GetMapping("/api/chat-rooms/search-tag")
    public List<GetChatRoomResponse> searchChatRoomsByTag(
        @RequestParam(name = "tag") String tag
    ) {
        return chatRoomService.searchChatRoomsByTag(tag);
    }

    @GetMapping("/api/chat-rooms/sse")
    public SseEmitter subscribeChatRoomChanges(@RequestParam(name = "userId") Long userId) {
        return chatRoomService.subscribeChatRoomChanges(userId);
    }

    @PatchMapping("/api/chat-rooms/{chatRoomId}")
    public GetChatRoomResponse modifyChatRoom(
        @PathVariable Long chatRoomId,
        @RequestBody ModifyChatRoomRequest request,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return chatRoomService.modifyChatRoom(chatRoomId, request, userDetails.getUser());
    }

    @DeleteMapping("/api/chat-rooms/{chatRoomId}")
    public Long deleteChatRoom(
        @PathVariable Long chatRoomId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return chatRoomService.deleteChatRoom(chatRoomId, userDetails.getUser());
    }
}
