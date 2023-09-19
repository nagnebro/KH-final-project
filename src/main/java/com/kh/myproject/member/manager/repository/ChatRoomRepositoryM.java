package com.kh.myproject.member.manager.repository;

import com.kh.myproject.member.chat.model.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;

public interface ChatRoomRepositoryM extends JpaRepository<ChatRoom, Long> {

    @Modifying
    @Transactional
    void deleteChatRoomByUser1UserNumberOrUser2UserNumber(Long userNumber1,Long userNumber2);

}
