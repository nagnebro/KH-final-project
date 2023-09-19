package com.kh.myproject.member.manager.service;

import com.kh.myproject.member.manager.repository.ChatMessageRepositoryM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ChatMessageServiceM {

    @Autowired
    ChatMessageRepositoryM chatMessageRepositoryM;

}
