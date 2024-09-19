package com.dungle.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dungle.models.Story;
import com.dungle.models.User;
import com.dungle.repository.StoryRepository;

@Service
public class StoryServiceImp implements StoryService{
    @Autowired
    private StoryRepository storyRepository;
    
    // @Autowired
    // private UserService userService;

    @Override
    public Story createStory(Story story, User user) {

        Story createStory = new Story();
        createStory.setCaption(story.getCaption());
        createStory.setImage(story.getImage());
        createStory.setUser(user);
        createStory.setTimestamp(LocalDateTime.now());
        return storyRepository.save(createStory);
    }

    @Override
    public List<Story> findStoryByUserId(Integer userId) throws Exception {
        // User user = userService.findUserById(userId);
        return storyRepository.findByUserId(userId);
    }
    
}
