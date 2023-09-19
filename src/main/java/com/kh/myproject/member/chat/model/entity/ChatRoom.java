package com.kh.myproject.member.chat.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kh.myproject.member.user.model.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class ChatRoom {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn( name = "userNumber1")
    private User user1;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn( name = "userNumber2")
    private User user2;


    @JsonIgnore
    @OneToMany(mappedBy = "chatRoom", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ChatMessage> messages = new ArrayList<>();




}
