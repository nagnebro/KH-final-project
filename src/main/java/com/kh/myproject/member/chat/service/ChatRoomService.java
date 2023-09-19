package com.kh.myproject.member.chat.service;

import com.kh.myproject.member.chat.model.dto.ChatRoomForm;
import com.kh.myproject.member.chat.model.entity.ChatRoom;
import com.kh.myproject.member.chat.repository.ChatRoomRepository;
import com.kh.myproject.member.user.model.entity.User;
import com.kh.myproject.member.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ChatRoomService {

    @Autowired
    ChatRoomRepository chatRoomRepository;

    @Autowired
    UserRepository userRepository;



    public List<ChatRoom> getChatRoomList(Long userNumber){


        return chatRoomRepository.selectChatRoomByUserNumber(userNumber);
    }

    public ChatRoom getChatRoomByRoomId(Long roomId){

        return chatRoomRepository.findById(roomId).orElse(null);
    }

    public void addChatRoom(Long writerNumber, Long userNumber){


        //이미 있는 방인지 없는 방인지도 검사해야한다.
        ChatRoom result = chatRoomRepository.findByUser1AndUser2(writerNumber,userNumber);
        if(result != null){ // 이미 생성된 대화방이 있었으면 대화방을 만들 필요가 없다.

            return;
        }

        log.info("작성자 {} 신청자 {}",writerNumber,userNumber);
        log.info("result값{}",result);

        User writer = userRepository.findById(writerNumber).orElse(null);
        User user = userRepository.findById(userNumber).orElse(null);

        ChatRoomForm chatRoomForm = new ChatRoomForm();
        chatRoomForm.setUser1(writer);
        chatRoomForm.setUser2(user);
        ChatRoom chatRoom = chatRoomForm.toEntity();
        chatRoomRepository.save(chatRoom);

    }
}
