package com.dungle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dungle.config.JwtConstant;
import com.dungle.models.Chat;
import com.dungle.models.User;
import com.dungle.request.CreateChatRequest;
import com.dungle.service.ChatService;
import com.dungle.service.UserService;

@RestController
@RequestMapping("/api/chats")
public class ChatController {
    
    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @PostMapping("")
    public Chat createChat(@RequestHeader(JwtConstant.JWT_HEADER) String jwt,@RequestBody CreateChatRequest req) throws Exception{
        User user = userService.findUserFromJwt(jwt);
        User friend = userService.findUserById(req.getFriendId());
        Chat chat = chatService.createChat(user,friend);
        return chat; 
    }
    @GetMapping("")
    public List<Chat> findUsersChat(@RequestHeader(JwtConstant.JWT_HEADER) String jwt,@RequestBody CreateChatRequest req){
        int userId = userService.findUserFromJwt(jwt).getId();
        List<Chat> chats = chatService.findusersChat(userId);
        return chats;
    }
}
