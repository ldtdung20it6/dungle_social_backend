package com.dungle.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dungle.models.Chat;
import com.dungle.models.User;
import com.dungle.repository.ChatRepository;

@Service
public class ChatServiceImp implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Chat createChat(User user, User friend) {
        Chat isExist = chatRepository.findChatByUsersId(user, friend);
        if ((isExist != null)) {
            return isExist;
        }
        Chat chat = new Chat();
        chat.getUsers().add(friend);
        chat.getUsers().add(user);
        chat.setTimestamp(LocalDateTime.now());
        return chatRepository.save(chat);
    }

    @Override
    public Chat findChatById(Integer chatId) throws Exception {
        Optional<Chat> opt = chatRepository.findById(chatId);
        if (opt.isEmpty()) {
            throw new Exception("Chat not found with id - " + chatId);
        }
        return opt.get();
    }

    @Override
    public List<Chat> findusersChat(Integer userId) {
        return chatRepository.findByUsersId(userId);
    }

}
