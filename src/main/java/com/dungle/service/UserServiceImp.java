package com.dungle.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dungle.config.JwtProvider;
import com.dungle.exceptions.UserException;
import com.dungle.models.User;
import com.dungle.repository.UserRepository;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public User regisUser(User userEntity) {
        User user = new User();
        user.setId(userEntity.getId());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setEmail(userEntity.getEmail());
        user.setPassword(userEntity.getPassword());
        User creatUser = userRepository.save(user);
        return creatUser;
    }

    @Override
    public User findUserById(Integer userId) throws UserException {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            return user.get();
        }
        throw new UserException("user not exist with userid " + userId);
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public User followUser(Integer userId,Integer friendId) throws UserException {
        User user = findUserById(userId);
        User friend = findUserById(friendId);

        user.getFollowings().add(friend.getId());
        friend.getFollowers().add(user.getId());

        userRepository.save(user);
        userRepository.save(friend);

        return user;
    }

    @Override
    public User updateUser(User userEntity,Integer userId) throws UserException {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new UserException("user not exist");
        }
        User dataUser = user.get();

        if(userEntity.getFirstName()!=null){
            dataUser.setFirstName(userEntity.getFirstName());
        }
        if(userEntity.getLastName()!=null){
            dataUser.setLastName(userEntity.getLastName());
        }
        if(userEntity.getEmail()!=null){
            dataUser.setEmail(userEntity.getEmail());
        }
        if(userEntity.getGender()!=null){
            dataUser.setGender(userEntity.getGender());
        }
        if(userEntity.getPassword()!=null){
            dataUser.setPassword(userEntity.getPassword());
        }
        User updateUser = userRepository.save(dataUser);
        return updateUser;
    }

    @Override
    public List<User> searchUser(String query) {
        return userRepository.searchUser(query);
    }

    @Override
    public User findUserFromJwt(String jwt) {
        String email = JwtProvider.getEmailFromJwtToken(jwt);
        User user = userRepository.findByEmail(email);
        return user;
    }
    
}
