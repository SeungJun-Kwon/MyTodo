package com.sparta.mytodo.domain.stomp;

import com.sparta.mytodo.domain.user.entity.User;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    private final Map<Long, SseEmitter> chatRoomEmitters = new ConcurrentHashMap<>();

    public List<GetChatRoomResponse> getChatRooms(User user) {
        return chatRoomRepository.findAllByNotInMember(user.getUserId()).stream()
            .map(chatRoom -> new GetChatRoomResponse(chatRoom.getUser().getUserName(), chatRoom))
            .toList();
    }

    public GetChatRoomResponse createChatRoom(CreateChatRoomRequest request, User user) {
        StringBuilder sb = tagListToString(request.getChatRoomTags());

        ChatRoom chatRoom = chatRoomRepository.save(
            ChatRoom.builder()
                .chatRoomName(request.getChatRoomName())
                .description(request.getDescription())
                .coverImage(request.getCoverImage())
                .chatRoomTag(sb.toString())
                .user(user)
                .build()
        );

        memberRepository.save(
            Member.builder().chatRoomId(chatRoom.getChatRoomId()).userId(user.getUserId()).build());

        return new GetChatRoomResponse(user.getUserName(), chatRoom);
    }

    public Long enterChatRoom(Long chatRoomId, User user) {
        Member member = memberRepository.save(
            Member.builder().chatRoomId(chatRoomId).userId(user.getUserId()).build());

        return member.getMemberId();
    }

    public List<GetChatRoomResponse> getMyChatRooms(User user) {
        return chatRoomRepository.findAllByMember(user.getUserId()).stream()
            .map(chatRoom -> new GetChatRoomResponse(chatRoom.getUser().getUserName(), chatRoom))
            .toList();
    }

    public List<GetChatRoomResponse> searchChatRoomsByKeyword(String keyword) {
        return chatRoomRepository.findAllByKeyword(keyword).stream()
            .map(chatRoom -> new GetChatRoomResponse(chatRoom.getUser().getUserName(), chatRoom))
            .toList();
    }

    public List<GetChatRoomResponse> searchChatRoomsByTag(String tag) {
        return chatRoomRepository.findAllByTag(tag).stream()
            .map(chatRoom -> new GetChatRoomResponse(chatRoom.getUser().getUserName(), chatRoom))
            .toList();
    }

    @Transactional
    public GetChatRoomResponse modifyChatRoom(Long chatRoomId, ModifyChatRoomRequest request,
        User user) {
        ChatRoom chatRoom = validateChatRoomOwner(chatRoomId, user.getUserId());

        StringBuilder sb = tagListToString(request.getChatRoomTags());
        chatRoom = chatRoom.update(request, sb.toString());

        GetChatRoomResponse response = new GetChatRoomResponse(user.getUserName(), chatRoom);

        sendChatRoomChanges("modifyChatRoom", response);

        return response;
    }

    @Transactional
    public Long deleteChatRoom(Long chatRoomId, User user) {
        ChatRoom chatRoom = validateChatRoomOwner(chatRoomId, user.getUserId());

        chatRoomRepository.delete(chatRoom);

        sendChatRoomChanges("deleteChatRoom", chatRoomId);

        return chatRoomId;
    }

    public SseEmitter subscribeChatRoomChanges(Long userId) {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        chatRoomEmitters.put(userId, sseEmitter);

        sseEmitter.onCompletion(() -> chatRoomEmitters.remove(userId));
        sseEmitter.onTimeout(() -> chatRoomEmitters.remove(userId));

        return sseEmitter;
    }

    public void sendChatRoomChanges(String eventName, Object data) {
        for (Long userId : chatRoomEmitters.keySet()) {
            SseEmitter sseEmitter = chatRoomEmitters.get(userId);
            try {
                sseEmitter.send(SseEmitter.event().name(eventName).data(data));
            } catch (IOException e) {
                chatRoomEmitters.remove(userId);
            }
        }
    }

    private StringBuilder tagListToString(List<String> tagList) {
        StringBuilder sb = new StringBuilder();

        if (tagList.isEmpty()) {
            return sb;
        }

        int i = 0;

        for (i = 0; i < tagList.size() - 1; i++) {
            sb.append(tagList.get(i)).append(",");
        }

        sb.append(tagList.get(i));

        return sb;
    }

    private ChatRoom validateChatRoomOwner(Long chatRoomId, Long userId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(
            () -> new NoSuchElementException("채팅방을 찾을 수 없음")
        );

        if (!chatRoom.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("채팅방 방장이 아님");
        }

        return chatRoom;
    }
}
