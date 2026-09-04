package org.example.expert.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.chat.dto.ChatRoomResponse;
import org.example.expert.domain.chat.dto.ChatUserResponse;
import org.example.expert.domain.chat.entity.ChatRoom;
import org.example.expert.domain.chat.repository.ChatRoomRepository;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserService chatRoomUserService;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ChatRoomResponse> getAllChatRoom() {
            return null;
    }

    public ChatRoomResponse createRoom(String nickname) {
       Optional<ChatRoom> chatRoom = chatRoomRepository.findByName(nickname);

       if (chatRoom.isPresent()) {
           throw new IllegalArgumentException("already exist");
       }

       User user = userRepository.findByNickname(nickname);

       ChatRoom newChatRoom = chatRoomRepository.save(new ChatRoom(nickname));
       chatRoomUserService.joinRoom(newChatRoom,user);

       List<User> userList = chatRoomUserService.getParticipantsInRoom(newChatRoom.getId());

       return ChatRoomResponse.from(newChatRoom, userList);

    }
}
