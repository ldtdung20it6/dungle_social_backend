package com.dungle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dungle.config.JwtConstant;
import com.dungle.models.Message;
import com.dungle.models.User;
import com.dungle.service.MessageService;
import com.dungle.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/messages")
public class MessageController {
    
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @PostMapping("/chat/{chatId}")
    public Message createMessage(@RequestBody Message req,@RequestHeader(JwtConstant.JWT_HEADER) String jwt,@PathVariable Integer chatId) throws Exception{

        User user = userService.findUserFromJwt(jwt);

        Message message = messageService.createMessage(user, chatId, req);
        return message;
    }

    @GetMapping("/chat/{chatId}")
    public List<Message> FindChatMessage(@RequestHeader(JwtConstant.JWT_HEADER) String jwt,@PathVariable Integer chatId) throws Exception{

        // User user = userService.findUserFromJwt(jwt);

        List<Message> messages = messageService.findChatMessages(chatId);
        return messages;
    }
}
