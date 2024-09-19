package com.dungle.service;

import java.util.List;

import com.dungle.models.Message;
import com.dungle.models.User;

public interface MessageService {

    public Message createMessage(User user,Integer chatId,Message req) throws Exception;

    public List<Message> findChatMessages(Integer chatId) throws Exception;
    
}