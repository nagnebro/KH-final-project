package com.kh.myproject.member.chat.repository;

import com.kh.myproject.member.chat.model.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {


    @Query("SELECT r, MAX(m.sendTime) AS time " +
            "FROM ChatRoom r " +
            "LEFT JOIN ChatMessage m " +
            "ON r.roomId = m.chatRoom.roomId " +
            "WHERE r.user1.userNumber = :userNumber OR r.user2.userNumber = :userNumber " +
            "GROUP BY r.roomId " +
            "ORDER BY time DESC")
    List<ChatRoom> selectChatRoomByUserNumber(@Param("userNumber")Long userNumber);
    // 조인을 이용해 message의 sendTime이 가장 최근인 방먼저 골라오도록 해야한다.
//    @Query("select ch from ChatRoom ch where ch.user1.userNumber = :userNumber or ch.user2 = :userNumber order by ch.roomId")
//    List<ChatRoom> selectChatRoomByUserNumber(@Param("userNumber")Long userNumber);
////
    @Query("select r from ChatRoom r where (r.user1.userNumber = :writerNumber and r.user2.userNumber = :userNumber) " +
            "or (r.user1.userNumber = :userNumber and r.user2.userNumber =:writerNumber) ")
    ChatRoom findByUser1AndUser2(@Param("writerNumber")Long writerNumber,@Param("userNumber")Long userNumber);
}
