package org.example.expert.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.chat.entity.ChatRoom;
import org.example.expert.domain.chat.entity.ChatRoomUser;
import org.example.expert.domain.chat.repository.ChatRoomUserRepository;
import org.example.expert.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class ChatRoomUserService {

    private final ChatRoomUserRepository chatRoomUserRepository;

    public void joinRoom(ChatRoom chatRoom, User user) {
        ChatRoomUser chatRoomUser = new ChatRoomUser(chatRoom, user);
        chatRoomUserRepository.save(chatRoomUser);
    }

    @Transactional(readOnly = true)
    public List<User> getParticipantsInRoom(Long chatRoomId) {
        return chatRoomUserRepository.findByChatRoomId(chatRoomId)
                .stream()
                .map(ChatRoomUser::getUser)
                .toList();
    }


}
