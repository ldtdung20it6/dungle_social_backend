package com.dungle.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.dungle.config.JwtConstant;
import com.dungle.models.Post;
import com.dungle.response.ApiResponse;
import com.dungle.service.PostService;
import com.dungle.service.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @GetMapping("")
    public ResponseEntity<List<Post>> findAllPost() throws Exception {
        List<Post> posts = postService.findAllPost();
        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Post> createPost(@RequestHeader(JwtConstant.JWT_HEADER) String jwt, @RequestBody Post post)
            throws Exception {
        int userId = userService.findUserFromJwt(jwt).getId();
        Post createPost = postService.createNewPost(post, userId);
        return new ResponseEntity<>(createPost, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/posts/{postId}/user")
    public ResponseEntity<ApiResponse> deletePost(@RequestHeader(JwtConstant.JWT_HEADER) String jwt,
            @PathVariable Integer postId) throws Exception {
        int userId = userService.findUserFromJwt(jwt).getId();
        String message = postService.deletePost(postId, userId);
        ApiResponse res = new ApiResponse(message, true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Post> findPostByIdHandler(@PathVariable Integer postId) throws Exception {
        Post post = postService.findPostById(postId);
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }

    @GetMapping("/posts/user/{userId}")
    public ResponseEntity<List<Post>> findUsersPost(@PathVariable Integer userId) throws Exception {
        List<Post> posts = postService.findPostByUserId(userId);
        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
    }

    @PutMapping("/posts/save/{postId}")
    public ResponseEntity<Post> savedPostHandler(@RequestHeader(JwtConstant.JWT_HEADER) String jwt,
            @PathVariable Integer postId) throws Exception {
        int userId = userService.findUserFromJwt(jwt).getId();
        Post post = postService.savedPost(postId, userId);
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }

    @PutMapping("/posts/like/{postId}")
    public ResponseEntity<Post> likePostHandler(@RequestHeader(JwtConstant.JWT_HEADER) String jwt,
            @PathVariable Integer postId) throws Exception {
        int userId = userService.findUserFromJwt(jwt).getId();
        Post post = postService.likePost(postId, userId);
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }

}
