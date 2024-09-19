package com.dungle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dungle.models.Chat;
import com.dungle.models.User;

public interface ChatRepository extends JpaRepository<Chat, Integer>{
    
    public List<Chat> findByUsersId(Integer userId);

    @Query("select c from Chat c where :user member of c.users and :friend member of c.users")
    public Chat findChatByUsersId(@Param("user") User user, @Param("friend") User friend);

}
