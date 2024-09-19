package com.dungle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dungle.config.JwtConstant;
import com.dungle.models.Comment;
import com.dungle.service.CommentService;
import com.dungle.service.UserService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @PostMapping("/post/{postId}")
    public Comment createComment(@RequestHeader(JwtConstant.JWT_HEADER) String jwt,@RequestBody Comment comment,@PathVariable Integer postId) throws Exception{
        int userId = userService.findUserFromJwt(jwt).getId();
        Comment createdComment = commentService.createComment(comment, postId, userId);
        return createdComment;
    }

    @PutMapping("/like/{commentId}")
    public Comment likeComment(@RequestHeader(JwtConstant.JWT_HEADER) String jwt,@PathVariable Integer commentId) throws Exception{
        int userId = userService.findUserFromJwt(jwt).getId();
        Comment likedComment = commentService.likeComment(commentId, userId);
        return likedComment;
    }
}
