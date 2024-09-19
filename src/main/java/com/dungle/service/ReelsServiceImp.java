package com.dungle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dungle.models.Reels;
import com.dungle.models.User;
import com.dungle.repository.ReelsRepository;

@Service
public class ReelsServiceImp implements ReelsService{

    @Autowired
    private ReelsRepository reelsRepository;

    @Override
    public Reels createReels(Reels reels, User user) {
        Reels createReel = new Reels();
        createReel.setTitle(reels.getTitle());
        createReel.setUser(user);
        createReel.setVideo(reels.getVideo());
        return reelsRepository.save(createReel);
    }

    @Override
    public List<Reels> findAllReels() {
        return reelsRepository.findAll();
    }

    @Override
    public List<Reels> findUserReels(Integer userId) throws Exception {
        return reelsRepository.findByUserId(userId);
    }
    
}
