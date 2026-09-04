package org.example.expert.domain.chat.repository;

import org.example.expert.domain.chat.entity.ChatRoomUser;
import org.example.expert.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {

    @Query("""
    SELECT c.user
    FROM ChatRoomUser c
    LEFT JOIN FETCH c.user
    WHERE c.chatRoom.id = :chatRoomId
""")
    List<ChatRoomUser> findByChatRoomId(@Param("chatRoomId") Long chatRoomId);
}
