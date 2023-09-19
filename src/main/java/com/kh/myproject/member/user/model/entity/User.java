package com.kh.myproject.member.user.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kh.myproject.community.accompany.entity.Accompany;
import com.kh.myproject.community.accompany.entity.Comment;
import com.kh.myproject.community.plan.model.entity.PlanBoard;
import com.kh.myproject.member.chat.model.entity.ChatRoom;
import com.kh.myproject.store.flight.model.entity.FlightTicketInfo;
import com.kh.myproject.store.rentcar.model.entity.RentReservationInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
//@AllArgsConstructor // 모든 멤버 변수를 초기화하는 생성자
@NoArgsConstructor // 기본 생성자.
@EqualsAndHashCode // equa
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// 기본키 값을 자동으로 생성한다.
    @Column(name = "user_number")       // 예슬 추가함(외래키)
    private Long userNumber;
    // user_number 필드는 auto_increment이면서 pk이다.



//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Accompany> accompanyList;

    // ArticleForm에서 생성한 멤버변수와 테이블을 매핑시키는 과정?
    @Column
    private String userId; // 유저 아이디

    @Column
    private String userName; // 유저 이름

    @Column
    private String userPassword; // 유저 패스워드

    @Column
    private String userPhone;  // 핸드폰 번호


    @Column
    private String userGender; // 유저 성별

    @Column
    private Date userDate; // 유저 생년월일

    @Column
    private String userMbti; // 유저 성향(mbti)

    @Column
    private String userImg; // 유저 프로필 이미지.

    @Column
    private LocalDateTime userRegdate; // 유저 가입날짜

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PlanBoard> planBoard = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FlightTicketInfo> flightTicketInfoList = new ArrayList<>();


    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RentReservationInfo> rentReservationInfoList  = new ArrayList<>();


    @JsonIgnore
    @OneToMany(mappedBy = "user1", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ChatRoom> user1   = new ArrayList<>();


    @JsonIgnore
    @OneToMany(mappedBy = "user2", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ChatRoom> user2   = new ArrayList<>();


    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Accompany> accompanyList   = new ArrayList<>();


    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> commentList   = new ArrayList<>();








    public User(Long userNumber, String userId, String userName, String userPassword, String userPhone, String userGender, Date userDate, String userMbti, String userImg, LocalDateTime userRegdate) {
        this.userNumber = userNumber;
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userPhone = userPhone;
        this.userGender = userGender;
        this.userDate = userDate;
        this.userMbti = userMbti;
        this.userImg = userImg;
        this.userRegdate = userRegdate;
    }
}
