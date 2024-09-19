package com.dungle.service;

import java.util.List;

import com.dungle.models.Story;
import com.dungle.models.User;

public interface StoryService {
    
    public Story createStory(Story story, User user);

    public List<Story> findStoryByUserId(Integer userId) throws Exception;
}
