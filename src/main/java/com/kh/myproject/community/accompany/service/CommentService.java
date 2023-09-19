package com.kh.myproject.community.accompany.service;

import com.kh.myproject.community.accompany.entity.Comment;
import com.kh.myproject.community.accompany.repository.CommentRepository;
import com.kh.myproject.member.user.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;


    public List<Comment> findByUser(User user){


        return commentRepository.findByUser(user);
    }


}
