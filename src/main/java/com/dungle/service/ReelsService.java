package com.dungle.service;

import java.util.List;

import com.dungle.models.Reels;
import com.dungle.models.User;

public interface ReelsService {
    
    public Reels createReels(Reels reels, User user);

    public List<Reels> findAllReels();

    public List<Reels> findUserReels(Integer userId) throws Exception;
}
