package com.dungle.service;

import java.util.List;

import com.dungle.exceptions.UserException;
import com.dungle.models.User;

public interface UserService {

    public User regisUser(User user);

    public User findUserById(Integer userId) throws UserException;

    public User findUserByEmail(String email);

    public User followUser(Integer userId,Integer friendId) throws UserException;

    public User updateUser(User user,Integer userId) throws UserException;

    public List<User> searchUser(String query);

    public User findUserFromJwt(String jwt);

}
