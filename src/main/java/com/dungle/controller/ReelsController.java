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
import com.dungle.models.Reels;
import com.dungle.models.User;
import com.dungle.service.ReelsService;
import com.dungle.service.UserService;

@RestController
@RequestMapping("/api/reels")
public class ReelsController {

    @Autowired
    private ReelsService reelsService;

    @Autowired
    private UserService userService;

    @PostMapping("")
    public Reels createReels(@RequestHeader(JwtConstant.JWT_HEADER) String jwt, @RequestBody Reels reels) {
        User user = userService.findUserFromJwt(jwt);
        Reels createReels = reelsService.createReels(reels, user);
        return createReels;
    }

    @GetMapping("")
    public List<Reels> findAllReels() {
        List<Reels> reels = reelsService.findAllReels();
        return reels;
    }

    @GetMapping("/user/{userId}")
    public List<Reels> findUserReels(@PathVariable Integer userId) throws Exception {
        List<Reels> reels = reelsService.findUserReels(userId);
        return reels;
    }
}
