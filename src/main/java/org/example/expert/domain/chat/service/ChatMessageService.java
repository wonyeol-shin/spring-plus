package org.example.expert.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.chat.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
}
