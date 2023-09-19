package com.kh.myproject.member.chat.model.dto;


import com.kh.myproject.member.chat.model.entity.ChatRoom;
import com.kh.myproject.member.user.model.entity.User;
import lombok.*;


@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ChatRoomForm {

    private Long roomId;
    private User user1;
    private User user2;


    public ChatRoom toEntity() {

        return ChatRoom.builder().
                roomId(roomId).
                user1(user1).
                user2(user2).build();

    }


}
