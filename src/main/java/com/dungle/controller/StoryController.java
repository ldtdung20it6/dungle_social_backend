package com.dungle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dungle.config.JwtConstant;
import com.dungle.models.Story;
import com.dungle.models.User;
import com.dungle.service.StoryService;
import com.dungle.service.UserService;

@RestController
@RequestMapping("/api/story")
public class StoryController {

    @Autowired
    private StoryService storyService;

    @Autowired
    private UserService userService;

    @PostMapping("")
    public Story createStory(@RequestHeader(JwtConstant.JWT_HEADER) String jwt,@RequestBody Story story){
        User user = userService.findUserFromJwt(jwt);
        Story createStory = storyService.createStory(story, user);
        return createStory;
        
    }
    @GetMapping("/user/{userId}")
    public List<Story> findUsersStory(@PathVariable Integer userId) throws Exception{
        List<Story> stories = storyService.findStoryByUserId(userId);
        return stories;
    }
    
}
