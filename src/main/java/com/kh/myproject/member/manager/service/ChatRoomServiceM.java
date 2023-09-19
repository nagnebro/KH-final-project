package com.kh.myproject.member.manager.service;

import com.kh.myproject.member.manager.repository.ChatRoomRepositoryM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ChatRoomServiceM {

    @Autowired
    ChatRoomRepositoryM chatRoomRepositoryM;

    public void deleteChatRoom(Long userNumber){

        // 해당 유저번호를 가진 채팅방을 삭제하낟.
        chatRoomRepositoryM.deleteChatRoomByUser1UserNumberOrUser2UserNumber(userNumber,userNumber);
    }


}
