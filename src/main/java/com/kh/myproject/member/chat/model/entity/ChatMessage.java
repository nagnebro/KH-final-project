package com.kh.myproject.member.chat.model.entity;


import com.kh.myproject.member.user.model.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@ToString
//@AllArgsConstructor // 모든 멤버 변수를 초기화하는 생성자
@NoArgsConstructor // 기본 생성자.
@EqualsAndHashCode // equa

public class ChatMessage {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// 기본키 값을 자동으로 생성한다.
    @Column(name = "message_id")       // 예슬 추가함(외래키)
    private Long messageId;


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn( name = "room_id")
    private ChatRoom chatRoom;


    @Column
    private String content; // 메시지 내용

    @Column
    private Date sendTime; // 전송 시각

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "sender_id")
    private User user; // 해당 메시지의 발신자  (테이블 컬럼명 : sender_id)




}
