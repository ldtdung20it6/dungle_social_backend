package com.dungle.service;

import java.util.List;

import com.dungle.models.Chat;
import com.dungle.models.User;

public interface ChatService {

    public Chat createChat(User user, User friend);

    public Chat findChatById(Integer chatId) throws Exception;

    public List<Chat> findusersChat(Integer userId);

}
