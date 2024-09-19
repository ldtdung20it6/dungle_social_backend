package com.dungle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dungle.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

}
