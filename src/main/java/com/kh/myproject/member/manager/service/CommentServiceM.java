package com.kh.myproject.member.manager.service;

import com.kh.myproject.member.manager.repository.CommentRepositoryM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceM {

    @Autowired
    CommentRepositoryM commentRepositoryM;

    public void deleteByUserUserNumber(Long userNumber){

        commentRepositoryM.deleteByUserUserNumber(userNumber);

    }
}
