package com.dungle.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dungle.models.Post;
import com.dungle.models.User;
import com.dungle.repository.PostRepository;
import com.dungle.repository.UserRepository;

@Service
public class PostServiceImp implements PostService{
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Override
    public Post createNewPost(Post post, Integer userId) throws Exception {

        User user = userService.findUserById(userId);
        Post newPost = new Post(); 
        newPost.setCaption(post.getCaption());
        newPost.setCreatedAt(LocalDateTime.now());
        newPost.setId(post.getId());
        newPost.setImage(post.getImage());
        newPost.setUser(user);
        newPost.setVideo(post.getVideo());
        postRepository.save(newPost);
        return newPost;
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if(post.getUser().getId()!=user.getId()){
            throw new Exception("you can't delete anothor users post");
        }
        postRepository.delete(post);
        return "post deleted successfully";
    }

    @Override
    public List<Post> findPostByUserId(Integer userId) {
        return postRepository.findPostByUserId(userId);
    }

    @Override
    public Post findPostById(Integer postId) throws Exception {
        Optional<Post> otp = postRepository.findById(postId);
        if(otp.isEmpty()){
            throw new Exception(); 
        }
        return otp.get();
    }

    @Override
    public List<Post> findAllPost() {
        return postRepository.findAll();
    }

    @Override
    public Post savedPost(Integer postId, Integer userId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if(user.getSavedPost().contains(post)){
            user.getSavedPost().remove(post);
        }
        else user.getSavedPost().add(post);
        userRepository.save(user);
        return post;
    }

    @Override
    public Post likePost(Integer postId, Integer userId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if(post.getLiked().contains(user)){
            post.getLiked().remove(user);
        }else{
            post.getLiked().add(user);
        }
        return postRepository.save(post);
    }
    
}
