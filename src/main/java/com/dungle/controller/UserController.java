package com.dungle.controller;

import org.springframework.web.bind.annotation.RestController;

import com.dungle.config.JwtConstant;
import com.dungle.models.User;
import com.dungle.repository.UserRepository;
import com.dungle.service.UserService;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable("userId") int id) throws Exception {
        User user = userService.findUserById(id);
        return user;
    }

    @PutMapping("/edit-users")
    public User updateUser(@RequestHeader(JwtConstant.JWT_HEADER) String jwt,@RequestBody User userEntity) throws Exception {
        int userId = userService.findUserFromJwt(jwt).getId();
        User updateUser = userService.updateUser(userEntity, userId);
        return updateUser;
    }

    @PutMapping("/users/follow/{friendId}")
    public User followUserhandler(@RequestHeader(JwtConstant.JWT_HEADER) String jwt,@PathVariable Integer friendId) throws Exception {
        int userId = userService.findUserFromJwt(jwt).getId();
        User user = userService.followUser(userId, friendId);
        user.setPassword("null");
        return user;
    }

    @GetMapping("/users/search")
    public List<User> searchUser(@RequestParam("query") String query) {
        List<User> user = userService.searchUser(query);
        return user;
    }

    @GetMapping("/users/profile")
    public User getUserfromToken(@RequestHeader(JwtConstant.JWT_HEADER) String jwt) {
        User user = userService.findUserFromJwt(jwt);
        user.setPassword("null");
        return user;
    }

    // @DeleteMapping("/users")
    // public String DeleteUser(@RequestParam int userId) throws Exception {
    // Optional<User> user = userRepository.findById(userId);
    // if(user.isEmpty()){
    // throw new Exception("user not exist");
    // }
    // User deleteUser = user.get();
    // userRepository.delete(deleteUser);
    // return "deleted user id " + userId;
    // }
}
