package com.kh.myproject.member.chat.service;


import com.kh.myproject.member.chat.model.entity.ChatMessage;
import com.kh.myproject.member.chat.model.entity.ChatRoom;
import com.kh.myproject.member.chat.repository.ChatMessageRepository;
import com.kh.myproject.member.chat.repository.ChatRoomRepository;
import com.kh.myproject.member.user.model.entity.User;
import com.kh.myproject.member.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ChatMessageService {

    @Autowired
    ChatMessageRepository chatMessageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChatRoomRepository chatRoomRepository;


    public ChatMessage saveMessage(Long roomId, Long userNumber,String content){

        ChatMessage chatMessage = new ChatMessage();
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElse(null);
        User user = userRepository.findById(userNumber).orElse(null);

        chatMessage.setUser(user);
        chatMessage.setChatRoom(chatRoom);
        chatMessage.setContent(content);
        chatMessage.setSendTime(new Date());


        ChatMessage saved_message = chatMessageRepository.save(chatMessage);

        return saved_message;

    }

    public List<ChatMessage> getAllMessage(Long roomId){

        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElse(null);
        List<ChatMessage> chatMessageList = chatMessageRepository.findAllByChatRoomOrderBySendTimeAsc(chatRoom);

        return chatMessageList;
    }

    public List<String> getLastMessageList(Long userNumber, List<ChatRoom> chatRoom){

        List<String> messageList = new ArrayList<>();
        Pageable pageable = PageRequest.of(0,1,Sort.by("sendTime").descending());
        String lastMessage = "";
        for(int i = 0 ; i < chatRoom.size(); i++) {
            Page<String> pageList = chatMessageRepository.selectLastMessageByChatRoom(chatRoom.get(i).getRoomId(), pageable);
            System.out.println(pageList.getContent());
            if(pageList.getContent().size() != 0){
                lastMessage = pageList.getContent().get(0);
            }else{
                lastMessage = "대화내용이 없습니다.";
            }
            messageList.add(lastMessage);
        }

        System.out.println(messageList);
        return messageList;
    }
}
